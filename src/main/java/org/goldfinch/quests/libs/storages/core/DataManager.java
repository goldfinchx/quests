package org.goldfinch.quests.libs.storages.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.goldfinch.quests.libs.storages.dbs.Hibernate;
import org.hibernate.SessionFactory;

@Accessors(chain = true, fluent = true)
@Getter(value = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
public abstract class DataManager<D extends DataObject<I>, I extends Serializable> {

    private final Hibernate hibernate;
    private final SessionFactory sessionFactory;
    private final LocalCacheManager<D, I> cacheManager;
    private final Class<D> dataClass;

    private Consumer<D> onLoad = $ -> {};
    private Consumer<D> onUnload = $ -> {};

    public DataManager(Class<D> dataClass, Hibernate hibernate) {
        this.cacheManager = new LocalCacheManager<>();
        this.hibernate = hibernate;
        this.sessionFactory = this.hibernate.getSessionFactory();
        this.dataClass = dataClass;
    }

    public D create(D data) {
        return this.hibernate.completeTransaction(session -> session.merge(data))
            .thenApply(d -> this.load(d.getId()))
            .join();
    }

    public void delete(D data) {
        if (this.isCached(data.getId())) {
            this.invalidate(data.getId());
        }

        this.hibernate.completeTransaction(session -> {
            session.remove(data);
            return null;
        });
    }

    public D get(I id) {
        return this.load(id);
    }

    public D load(I id) {
        if (this.isCached(id)) {
            return this.getCached(id);
        }

        if (this.isExists(id)) {
            final D data = this.getRemote(id);
            this.onLoad.accept(data);
            this.cache(data);
            return data;
        }

        throw new NoSuchElementException("Data Element with id " + id + " not found");
    }

    protected void cache(D data) {
        this.cacheManager.save(data);
    }

    protected boolean isExists(I id) {
        return this.hibernate.completeTransaction(session -> session.get(this.dataClass, id) != null).join();
    }

    protected D getRemote(I id) {
        return this.hibernate.completeTransaction(session -> session.get(this.dataClass, id)).join();
    }

    protected boolean isDetached(D data) {
        try (final EntityManager em = this.hibernate.getSessionFactory().createEntityManager()) {
            return data.getId() != null
                          && !em.contains(data)
                          && em.find(data.getClass(), data.getId()) != null;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected boolean isCached(I id) {
        return this.cacheManager.isCached(id);
    }

    protected D getCached(I id) {
        return this.cacheManager.get(id);
    }

    public void unloadAll() {
        this.cacheManager.getAll().forEach((id, data) -> this.unload(id));
    }

    public void unload(I id) {
        if (!this.isCached(id)) {
            return;
        }

        final D data = this.getCached(id);
        this.onUnload.accept(data);
        this.save(id);
        this.invalidate(id);
    }

    public void save(I id) {
        if (!this.isCached(id)) {
            return;
        }

        final D data = this.getCached(id);
        this.updateRemote(data);
    }

    protected D updateRemote(D data) {
        return this.hibernate.completeTransaction(session -> session.merge(data)).join();
    }

    protected void invalidate(I id) {
        this.cacheManager.invalidate(id);
    }

    public Map<I, D> getCache() {
        return this.cacheManager.getAll();
    }
    public Map<I, D> getStorage() {
        final Map<I, D> storage = new HashMap<>();
        final TypedQuery<D> allQuery = this.hibernate.completeOperation(session -> {
            final CriteriaBuilder cb = session.getCriteriaBuilder();
            final CriteriaQuery<D> cq = cb.createQuery(this.dataClass);
            final Root<D> rootEntry = cq.from(this.dataClass);
            final CriteriaQuery<D> all = cq.select(rootEntry);
            return session.createQuery(all);
        }).join();

        for (final D data : allQuery.getResultList()) {
            storage.put(data.getId(), data);
        }

        return storage;
    }

    protected abstract D getTemplate();
}

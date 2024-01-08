package org.goldfinch.quests.data;

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
import org.goldfinch.quests.Quests;
import org.hibernate.Session;
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

    public DataManager(Class<D> dataClass) {
        this.cacheManager = new LocalCacheManager<>();
        this.hibernate = Quests.getInstance().getHibernate();
        this.sessionFactory = this.hibernate.getSessionFactory();
        this.dataClass = dataClass;
    }

    public D create(D data) {
        this.hibernate.completeOperation(session -> session.persist(data));
        this.load(data.getId());
        return data;
    }

    public D get(I id) {
        return this.isCached(id) ? this.getCached(id) : this.load(id);
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
        try (final Session session = this.hibernate.getSessionFactory().openSession()) {
            return session.get(this.dataClass, id) != null;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected D getRemote(I id) {
        try (final Session session = this.hibernate.getSessionFactory().openSession()) {
            return session.get(this.dataClass, id);
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

    protected void updateRemote(D data) {
        this.hibernate.completeOperation(session -> session.merge(data));
    }

    protected void invalidate(I id) {
        this.cacheManager.invalidate(id);
    }

    public Map<I, D> getCache() {
        return this.cacheManager.getAll();
    }
    public Map<I, D> getStorage() {
        final Map<I, D> storage = new HashMap<>();
        final TypedQuery<D> allQuery;

        try (final Session session = this.sessionFactory.openSession()) {
            final CriteriaBuilder cb = session.getCriteriaBuilder();
            final CriteriaQuery<D> cq = cb.createQuery(this.dataClass);
            final Root<D> rootEntry = cq.from(this.dataClass);
            final CriteriaQuery<D> all = cq.select(rootEntry);
            allQuery = session.createQuery(all);

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        for (final D data : allQuery.getResultList()) {
            storage.put(data.getId(), data);
        }

        return storage;
    }

    protected abstract D getTemplate();
}

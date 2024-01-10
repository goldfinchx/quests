package org.goldfinch.quests.libs.storages.dbs;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@Getter
public class Hibernate {

    private final SessionFactory sessionFactory;

    static {
        Thread.currentThread().setContextClassLoader(Hibernate.class.getClassLoader());
    }

    public Hibernate() {
        this.sessionFactory = this.buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

        try {
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (this.sessionFactory == null) {
            return;
        }

        this.sessionFactory.close();
    }

    public <R> CompletableFuture<R> completeTransaction(Function<Session, R> function) {
        try (final Session session = this.sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final R result = function.apply(session);
            transaction.commit();

            return CompletableFuture.supplyAsync(() -> result).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    public <R> CompletableFuture<R> completeOperation(Function<Session, R> function) {
        try (final Session session = this.sessionFactory.openSession()) {
            return CompletableFuture.supplyAsync(() -> function.apply(session))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

}

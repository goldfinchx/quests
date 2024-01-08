package org.goldfinch.quests.data;

import java.util.function.Consumer;
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

    public void completeOperation(Consumer<Session> consumer) {
        try (final Session session = this.sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}

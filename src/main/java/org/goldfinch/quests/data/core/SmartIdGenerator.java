package org.goldfinch.quests.data.core;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IncrementGenerator;

public class SmartIdGenerator extends IncrementGenerator implements IdentifierGenerator {

    @Override
    public synchronized Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        final Serializable id = (Serializable) session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        final Serializable id = (Serializable) session.getEntityPersister(null, owner).getClassMetadata().getIdentifier(owner, session);
        return id != null ? id : super.generate(session, owner, currentValue, eventType);
    }

}

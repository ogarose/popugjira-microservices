package com.ogarose.popugjira.common.types;

public class DomainEvent<T extends EntityId> {
    protected final T EntityId;

    protected DomainEvent(T entityId) {
        EntityId = entityId;
    }
}

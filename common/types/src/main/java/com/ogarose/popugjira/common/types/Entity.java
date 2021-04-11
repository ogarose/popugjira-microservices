package com.ogarose.popugjira.common.types;


import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Entity<T extends EntityId> {
    protected List<DomainEvent<T>> domainEvents = new ArrayList<>();
    @Getter(AccessLevel.PUBLIC)
    protected T id;

    private boolean suppressEvent = false;

    public List<DomainEvent<T>> popEvents() {
        List<DomainEvent<T>> returnEvents = domainEvents;
        domainEvents = new ArrayList<>();
        return returnEvents;
    }

    protected void applyEvent(DomainEvent<T> event) {
        if (suppressEvent) return;
        this.domainEvents.add(event);
    }

    protected void suppressEvent() {
        suppressEvent = true;
    }

    protected void unSuppressedEvent() {
        suppressEvent = false;
    }
}

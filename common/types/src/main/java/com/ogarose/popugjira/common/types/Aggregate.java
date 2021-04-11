package com.ogarose.popugjira.common.types;

import java.util.Collection;

public class Aggregate<T extends EntityId> extends Entity<T> {

    Collection<DomainEvent> domainEvents;
}

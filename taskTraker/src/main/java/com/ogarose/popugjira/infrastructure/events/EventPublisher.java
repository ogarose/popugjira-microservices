package com.ogarose.popugjira.infrastructure.events;

import com.ogarose.popugjira.common.types.DomainEvent;
import com.ogarose.popugjira.common.types.EntityId;

import java.util.List;

public interface EventPublisher {
    <T extends EntityId> void publish(List<DomainEvent<T>> events);
}

package com.ogarose.popugjira.infrastructure.events;

import com.ogarose.popugjira.common.types.DomainEvent;
import com.ogarose.popugjira.common.types.EntityId;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventPublisherImp implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public <T extends EntityId> void publish(List<DomainEvent<T>> events) {
        events.forEach(applicationEventPublisher::publishEvent);
    }
}

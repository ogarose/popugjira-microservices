package com.ogarose.popugjira.common.messaging.traker.biz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
public class TaskAssigned {
    private UUID id;
    private UUID userId;

    public TaskAssigned(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }
}

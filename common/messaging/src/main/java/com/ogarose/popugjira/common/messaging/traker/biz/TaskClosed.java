package com.ogarose.popugjira.common.messaging.traker.biz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
public class TaskClosed {
    private UUID id;
    private UUID assignedTo;

    public TaskClosed(UUID id, UUID assignedTo) {
        this.id = id;
        this.assignedTo = assignedTo;
    }
}

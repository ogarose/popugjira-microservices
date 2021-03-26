package com.ogarose.popugjira.domain.todo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskCreatedEvent {
    private UUID id;
    private Task task;

    public TaskCreatedEvent(UUID id, Task task) {
        this.id = id;
        this.task = task;
    }
}

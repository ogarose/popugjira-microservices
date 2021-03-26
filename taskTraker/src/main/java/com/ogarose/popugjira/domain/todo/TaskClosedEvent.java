package com.ogarose.popugjira.domain.todo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskClosedEvent {
    private UUID taskId;
    private Task task;

    public TaskClosedEvent(UUID taskId, Task task) {
        this.taskId = taskId;
        this.task = task;
    }
}

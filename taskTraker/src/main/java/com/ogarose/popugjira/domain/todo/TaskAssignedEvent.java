package com.ogarose.popugjira.domain.todo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskAssignedEvent {
    private UUID taskId;
    private UUID assignedTo;
    private Task task;

    public TaskAssignedEvent(Task task, UUID taskId, UUID assignedTo) {
        this.task = task;
        this.taskId = taskId;
        this.assignedTo = assignedTo;
    }
}

package com.ogarose.popugjira.domain.todo;

import com.ogarose.popugjira.common.types.DomainEvent;
import lombok.Getter;

import java.util.UUID;

public class TaskEvent {

    private TaskEvent() {
    }

    @Getter
    public static class Created extends DomainEvent<TaskId> {
        private final Task task;

        public Created(TaskId entityId, Task task) {
            super(entityId);
            this.task = task;
        }
    }

    @Getter
    public static class Closed extends DomainEvent<TaskId> {
        private final Task task;

        public Closed(TaskId taskId, Task task) {
            super(taskId);
            this.task = task;
        }
    }

    @Getter
    public static class Assigned extends DomainEvent<TaskId> {
        private final UUID assignedTo;
        private final Task task;

        public Assigned(Task task, TaskId taskId, UUID assignedTo) {
            super(taskId);
            this.task = task;
            this.assignedTo = assignedTo;
        }
    }
}

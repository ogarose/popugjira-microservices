package com.ogarose.popugjira.domain.todo;

import com.ogarose.popugjira.common.types.Aggregate;
import com.ogarose.popugjira.common.types.DomainException;
import com.ogarose.popugjira.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Task extends Aggregate<TaskId> {
    private TaskTitle title;
    private Status status;
    private UUID assignToId;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public Task() {
    }

    private Task(TaskId id, TaskTitle title, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Task createNew(TaskIdGenerator taskIdGenerator, TaskTitle taskTitle) {
        Task createdTask = new Task(taskIdGenerator.generate(), taskTitle, Status.OPEN, LocalDateTime.now());
        createdTask.applyEvent(new TaskEvent.Created(createdTask.getId(), createdTask));
        return createdTask;
    }

    public static Task restore(TaskId id, TaskTitle title, Status status, LocalDateTime createdAt, UUID assignToId, LocalDateTime closedAt) {
        Task restoredTask = new Task(
                id,
                title,
                status,
                createdAt
        );

        restoredTask.assignToId = assignToId;
        restoredTask.closedAt = closedAt;

        return restoredTask;
    }

    public void close() {
        if (!status.canBeChangeTo(Status.CLOSED)) {
            throw new DomainException("Can no be change to closed");
        }

        status = Status.CLOSED;
        closedAt = LocalDateTime.now();

        this.applyEvent(new TaskEvent.Closed(this.id, this));
    }

    public void reopen() {
        if (!status.canBeChangeTo(Status.OPEN)) {
            throw new DomainException("Can no be change to open");
        }

        status = Status.OPEN;
    }

    public void assignTo(User user) {
        assignToId = user.getId();

        applyEvent(new TaskEvent.Assigned(this, this.id, this.assignToId));
    }
}

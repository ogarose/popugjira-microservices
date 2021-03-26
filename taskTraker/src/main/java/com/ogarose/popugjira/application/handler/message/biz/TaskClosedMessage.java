package com.ogarose.popugjira.application.handler.message.biz;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class TaskClosedMessage {
    private UUID id;
    private UUID assignedTo;
    private String type = "taskClosed";

    public TaskClosedMessage(UUID id, UUID assignedTo) {
        this.id = id;
        this.assignedTo = assignedTo;
    }
}

package com.ogarose.popugjira.application.handler.message.biz;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class TaskAssignedMessage {
    private UUID id;
    private UUID userId;
    private String type = "taskAssigned";

    public TaskAssignedMessage(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }
}

package com.ogarose.popugjira.application.handler.message.cud;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
public class TaskCreatedMessage {
    private UUID id;
    private String type = "taskCreated";

    public TaskCreatedMessage(UUID id) {
        this.id = id;
    }
}

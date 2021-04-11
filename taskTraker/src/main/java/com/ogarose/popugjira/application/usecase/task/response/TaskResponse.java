package com.ogarose.popugjira.application.usecase.task.response;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TaskResponse {
    Integer id;
    String title;
    LocalDateTime createdAt;
    LocalDateTime closedAt;
}

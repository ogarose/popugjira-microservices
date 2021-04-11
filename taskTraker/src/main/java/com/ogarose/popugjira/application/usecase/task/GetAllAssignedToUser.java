package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.usecase.task.response.TaskResponse;

import java.util.List;
import java.util.UUID;

public interface GetAllAssignedToUser {
    public List<TaskResponse> execute(UUID userId);
}

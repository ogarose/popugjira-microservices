package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.usecase.task.response.TaskResponse;

import java.util.List;

public interface GetAllOpened {
    public List<TaskResponse> execute();
}

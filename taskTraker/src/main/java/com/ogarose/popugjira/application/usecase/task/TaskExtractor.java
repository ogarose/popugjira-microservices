package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskExtractor {
    Optional<Task> find(TaskId.TaskInternalId id);

    List<Task> findAllOpened();

    List<Task> findAllClosed();
}

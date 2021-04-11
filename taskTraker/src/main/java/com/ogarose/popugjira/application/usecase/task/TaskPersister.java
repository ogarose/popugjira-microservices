package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.domain.todo.Task;

public interface TaskPersister {
    void save(Task task);
}

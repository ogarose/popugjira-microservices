package com.ogarose.popugjira.domain.todo;

import java.util.List;
import java.util.Optional;

public interface TaskExtractor {
    Optional<Task> find(TaskId.TaskInternalId id);

    List<Task> findAllOpened();

    List<Task> findAllClosed();
}

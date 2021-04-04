package com.ogarose.popugjira.accounting.domain.task;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    Optional<Task> findById(UUID id);

    Task save(Task task);
}

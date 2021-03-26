package com.ogarose.popugjira.domain.todo;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> find(Integer id);

    List<Task> findAllOpened();

    List<Task> findAllClosed();

    Task save(Task task);
}

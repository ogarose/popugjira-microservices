package com.ogarose.popugjira.accounting.infrastructure.persistence.task;

import com.ogarose.popugjira.accounting.domain.task.Task;
import com.ogarose.popugjira.accounting.domain.task.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskRepositoryImp implements TaskRepository {
    private final TaskRepositoryJpa taskRepositoryJpa;

    @Override
    public Optional<Task> findById(UUID id) {
        return taskRepositoryJpa.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepositoryJpa.save(task);
    }
}

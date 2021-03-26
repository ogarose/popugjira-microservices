package com.ogarose.popugjira.infrastructure.persistence.repository.todo;

import com.ogarose.popugjira.domain.todo.Status;
import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskRepositoryImp implements TaskRepository {

    private final TaskRepositoryJpa taskRepositoryJpa;

    @Override
    public Optional<Task> find(Integer id) {
        return taskRepositoryJpa.findById(id);
    }

    @Override
    public List<Task> findAllOpened() {
        return taskRepositoryJpa.findAllByStatusOrderByCreatedAtDesc(Status.OPEN);
    }

    @Override
    public List<Task> findAllClosed() {
        return taskRepositoryJpa.findAllByStatusOrderByCreatedAtDesc(Status.CLOSED);
    }

    @Override
    public Task save(Task task) {
        return taskRepositoryJpa.save(task);
    }
}

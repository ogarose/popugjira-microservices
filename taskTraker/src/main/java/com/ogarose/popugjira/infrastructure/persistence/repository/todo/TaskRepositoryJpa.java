package com.ogarose.popugjira.infrastructure.persistence.repository.todo;

import com.ogarose.popugjira.domain.todo.Status;
import com.ogarose.popugjira.domain.todo.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepositoryJpa extends CrudRepository<Task, Integer> {
    List<Task> findAllByStatusOrderByCreatedAtDesc(Status status);
}

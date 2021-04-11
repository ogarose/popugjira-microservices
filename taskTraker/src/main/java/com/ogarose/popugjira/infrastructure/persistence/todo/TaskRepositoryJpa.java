package com.ogarose.popugjira.infrastructure.persistence.todo;

import com.ogarose.popugjira.domain.todo.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepositoryJpa extends CrudRepository<TaskEntity, Integer> {
    List<TaskEntity> findAllByStatusOrderByCreatedAtDesc(Status status);
}

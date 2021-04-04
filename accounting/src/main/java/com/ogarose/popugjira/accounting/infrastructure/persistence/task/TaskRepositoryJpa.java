package com.ogarose.popugjira.accounting.infrastructure.persistence.task;

import com.ogarose.popugjira.accounting.domain.task.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TaskRepositoryJpa extends CrudRepository<Task, UUID> {
}

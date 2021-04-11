package com.ogarose.popugjira.infrastructure.persistence.todo;

import com.ogarose.popugjira.domain.todo.*;
import com.ogarose.popugjira.infrastructure.events.EventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskRepositoryImp implements TaskPersister, TaskExtractor {
    private final TaskRepositoryJpa taskRepositoryJpa;
    private final TaskEntityMapper taskEntityMapper;
    private final EventPublisher eventPublisher;


    @Override
    public Optional<Task> find(TaskId.TaskInternalId id) {
        Optional<TaskEntity> taskEntityOptional = taskRepositoryJpa.findById(id.getInternalId());

        return taskEntityOptional.map(taskEntityMapper::toModel);
    }

    @Override
    public List<Task> findAllOpened() {
        return taskEntityMapper.toModels(
                taskRepositoryJpa.findAllByStatusOrderByCreatedAtDesc(Status.OPEN)
        );
    }

    @Override
    public List<Task> findAllClosed() {
        return taskEntityMapper.toModels(
                taskRepositoryJpa.findAllByStatusOrderByCreatedAtDesc(Status.CLOSED)
        );
    }

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = taskEntityMapper.toEntity(task);

        taskRepositoryJpa.save(taskEntity);

        eventPublisher.publish(task.popEvents());

        //@todo do not need to return task object
        return task;
    }
}

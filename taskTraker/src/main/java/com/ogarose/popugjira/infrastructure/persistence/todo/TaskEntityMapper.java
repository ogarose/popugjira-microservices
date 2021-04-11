package com.ogarose.popugjira.infrastructure.persistence.todo;

import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskId;
import com.ogarose.popugjira.domain.todo.TaskTitle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TaskEntityMapper {
    public Task toModel(TaskEntity taskEntity) {
        return Task.restore(
                new TaskId(new TaskId.TaskInternalId(taskEntity.getId()), taskEntity.getPublicId()),
                new TaskTitle(taskEntity.getTitle()),
                taskEntity.getStatus(),
                taskEntity.getCreatedAt(),
                taskEntity.getAssignToId(),
                taskEntity.getClosedAt()
        );
    }

    @Mapping(target = "id", expression = "java(task.getId().getInternalId().value())")
    @Mapping(target = "publicId", expression = "java(task.getId().getPublicId())")
    @Mapping(target = "title", expression = "java(String.valueOf(task.getTitle()))")
    public abstract TaskEntity toEntity(Task task);

    public abstract List<Task> toModels(List<TaskEntity> taskEntity);

    abstract String map(TaskTitle value);
}

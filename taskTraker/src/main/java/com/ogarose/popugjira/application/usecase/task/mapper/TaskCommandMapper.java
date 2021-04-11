package com.ogarose.popugjira.application.usecase.task.mapper;

import com.ogarose.popugjira.application.usecase.task.command.TaskCommand;
import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskTitle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskCommandMapper {
    @Mapping(target = "id", expression = "java(task.getId().getInternalId().value())")
    TaskCommand toCommand(Task task);

    String map(TaskTitle value);
}

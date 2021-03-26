package com.ogarose.popugjira.application.mapper;

import com.ogarose.popugjira.application.command.TaskCommand;
import com.ogarose.popugjira.domain.todo.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskCommandMapper {
    Task toDomain(TaskCommand taskCommand);

    TaskCommand toCommand(Task task);
}

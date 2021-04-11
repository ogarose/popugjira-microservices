package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.usecase.task.command.TaskCommand;
import com.ogarose.popugjira.application.usecase.task.mapper.TaskCommandMapper;
import com.ogarose.popugjira.domain.todo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateNewUseCase implements CreateNew {
    private final TaskPersister taskPersister;
    private final TaskCommandMapper taskCommandMapper;
    private final TaskIdGenerator taskGenerator;

    @Override
    public TaskCommand createNew(TaskCommand taskCommand) {
        Task task = Task.createNew(taskGenerator, new TaskTitle(taskCommand.getTitle()));
        taskPersister.save(task);

        return taskCommandMapper.toCommand(task);
    }
}

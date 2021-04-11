package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.usecase.task.command.TaskCommand;

public interface CreateNew {
    TaskCommand createNew(TaskCommand taskCommand);
}

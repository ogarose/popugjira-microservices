package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.exceptions.NotFoundException;
import com.ogarose.popugjira.domain.todo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReopenTaskUseCase implements ReopenTask {
    private final TaskExtractor taskExtractor;
    private final TaskPersister taskPersister;

    @Override
    public void reopen(Integer taskId) {
        Optional<Task> optionalTask = taskExtractor.find(new TaskId.TaskInternalId(taskId));
        if (optionalTask.isEmpty()) {
            throw new NotFoundException();
        }

        Task task = optionalTask.get();
        task.reopen();

        taskPersister.save(task);
    }
}

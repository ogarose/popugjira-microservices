package com.ogarose.popugjira.application.usecase.task;

import com.ogarose.popugjira.application.usecase.task.response.TaskResponse;
import com.ogarose.popugjira.domain.todo.TaskExtractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllClosedUseCase implements GetAllClosed {
    private final TaskExtractor taskExtractor;

    @Override
    public List<TaskResponse> execute() {
        return taskExtractor.findAllClosed().stream()
                .map(task -> new TaskResponse(
                        task.getId().getInternalId().value(),
                        task.getTitle().toString(),
                        task.getCreatedAt(),
                        task.getClosedAt()
                )).collect(Collectors.toList());
    }
}

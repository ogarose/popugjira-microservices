package com.ogarose.popugjira.accounting.application.handler;

import com.ogarose.popugjira.accounting.usecase.user.DecreaseBalanceByTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DecreaseBalanceByTaskWhenTaskAssignedHandler {
    private final DecreaseBalanceByTaskUseCase decreaseBalanceByTask;

    public void handle(UUID userId, UUID taskId) {
        decreaseBalanceByTask.execute(userId, taskId);
    }
}

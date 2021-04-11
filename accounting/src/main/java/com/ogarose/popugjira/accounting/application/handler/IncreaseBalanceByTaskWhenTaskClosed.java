package com.ogarose.popugjira.accounting.application.handler;

import com.ogarose.popugjira.accounting.usecase.user.IncreaseBalanceByTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class IncreaseBalanceByTaskWhenTaskClosed {
    private final IncreaseBalanceByTaskUseCase increaseBalanceByTaskUseCase;

    public void handle(UUID userId, UUID taskId) {
        increaseBalanceByTaskUseCase.execute(userId, taskId);
    }
}

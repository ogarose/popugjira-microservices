package com.ogarose.popugjira.accounting.usecase.user;

import com.ogarose.popugjira.accounting.domain.task.Task;
import com.ogarose.popugjira.accounting.domain.task.TaskRepository;
import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DecreaseBalanceByTaskUseCase {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public void execute(UUID userId, UUID taskId) {
        Task assignedTask = taskRepository.findById(taskId).orElseThrow();
        User targetUser = userRepository.find(userId).orElseThrow();

        targetUser.decreaseBalance(assignedTask.getOpenPrice());
        userRepository.save(targetUser);

        Transaction newTransaction = Transaction.createCreditTaskTransaction(assignedTask.getOpenPrice(),
                "Task was assigned to user",
                assignedTask,
                targetUser);
        transactionRepository.save(newTransaction);
    }
}

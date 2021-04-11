package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.domain.task.Task;
import com.ogarose.popugjira.accounting.domain.task.TaskRepository;
import com.ogarose.popugjira.accounting.domain.user.*;
import com.ogarose.popugjira.accounting.infrastructure.messaging.MessageBus;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
class TaskBizConsumerTest {
    @Autowired
    MessageBus messageBus;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void whenTaskAssignedThenUsersBalanceDecreased() throws InterruptedException {
        Task usersTask = new Task(UUID.randomUUID());
        taskRepository.save(usersTask);

        User admin  = new User(UUID.randomUUID(),"admin", Role.ROLE_ADMIN, "someEmail@tut.vt", "230893");
        userRepository.save(admin);

        TaskAssigned incomeMessage = new TaskAssigned(usersTask.getId(), admin.getId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, incomeMessage);

        Thread.sleep(10000);

        User updatedAdmin = userRepository.find(admin.getId()).orElseThrow();
        Assertions.assertEquals(Math.abs(updatedAdmin.getBalance()), (int) usersTask.getOpenPrice());

        List<Transaction> allOfDayByUser = transactionRepository.findAllOfDayByUser(admin);
        Assertions.assertTrue(allOfDayByUser.size() == 1);

        Transaction firstTransaction = allOfDayByUser.get(0);
        Assertions.assertEquals(firstTransaction.getCredit(), usersTask.getOpenPrice());
    }

    @Test
    void whenTaskClosedThenUsersBalanceIncreased() throws InterruptedException {
        Task usersTask = new Task(UUID.randomUUID());
        taskRepository.save(usersTask);

        User admin  = new User(UUID.randomUUID(),"admin 2", Role.ROLE_ADMIN, "someEmail2@tut.vt", "23089322");
        userRepository.save(admin);

        TaskClosed incomeMessage = new TaskClosed(usersTask.getId(), admin.getId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, incomeMessage);

        Thread.sleep(10000);

        User updatedAdmin = userRepository.find(admin.getId()).orElseThrow();
        Assertions.assertEquals(Math.abs(updatedAdmin.getBalance()), (int) usersTask.getClosePrice());

        List<Transaction> allOfDayByUser = transactionRepository.findAllOfDayByUser(admin);
        Assertions.assertTrue(allOfDayByUser.size() == 1);

        Transaction firstTransaction = allOfDayByUser.get(0);
        Assertions.assertEquals(firstTransaction.getDebit(), usersTask.getClosePrice());
    }
}

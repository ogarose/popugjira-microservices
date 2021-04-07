package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.domain.task.Task;
import com.ogarose.popugjira.accounting.domain.task.TaskRepository;
import com.ogarose.popugjira.accounting.infrastructure.messaging.MessageBus;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
public class TaskCudConsumerTest {
    @Autowired
    MessageBus messageBus;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void messageTaskCreatedMakeNewTaskWithPrices() throws InterruptedException {
        TaskCreated incomeMessage = new TaskCreated(UUID.randomUUID());

        messageBus.sendMessage(MessageTopics.TASK_CUD, incomeMessage);

        Thread.sleep(4000);

        Optional<Task> createdTaskOption = taskRepository.findById(incomeMessage.getId());
        Assertions.assertTrue(createdTaskOption.isPresent());

        Task createdTask = createdTaskOption.get();
        Assertions.assertTrue(createdTask.getClosePrice() > 0);
        Assertions.assertTrue(createdTask.getOpenPrice() > 0);
    }
}

package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.domain.task.Task;
import com.ogarose.popugjira.accounting.domain.task.TaskRepository;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.TASK_CUD)
public class TaskCudConsumer {
    private final TaskRepository taskRepository;

    @KafkaHandler
    public void listener(TaskCreated taskCreated) {
        Task newTask = new Task(taskCreated.getId());
        taskRepository.save(newTask);
    }
}

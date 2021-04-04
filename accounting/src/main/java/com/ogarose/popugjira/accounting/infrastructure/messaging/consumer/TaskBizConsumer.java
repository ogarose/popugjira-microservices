package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.application.handler.TaskWasAssignedHandler;
import com.ogarose.popugjira.accounting.application.handler.TaskWasClosedHandler;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.TASK_BIZ)
public class TaskBizConsumer {
    private final TaskWasAssignedHandler taskWasAssignedHandler;
    private final TaskWasClosedHandler taskWasClosedHandler;

    @KafkaHandler
    public void listener(TaskAssigned taskAssigned) {
        taskWasAssignedHandler.handle(taskAssigned.getUserId(), taskAssigned.getId());
    }

    @KafkaHandler
    public void listener(TaskClosed taskClosed) {
        taskWasClosedHandler.handle(taskClosed.getAssignedTo(), taskClosed.getId());
    }
}

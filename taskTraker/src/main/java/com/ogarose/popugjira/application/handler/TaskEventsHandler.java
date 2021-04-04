package com.ogarose.popugjira.application.handler;

import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import com.ogarose.popugjira.application.notificator.EmailNotificator;
import com.ogarose.popugjira.application.notificator.SmsNotificator;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.domain.todo.TaskAssignedEvent;
import com.ogarose.popugjira.domain.todo.TaskClosedEvent;
import com.ogarose.popugjira.domain.todo.TaskCreatedEvent;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import com.ogarose.popugjira.infrastructure.messaging.MessageBus;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @todo looks like message bug, topiks and message object should be on infrastructure level
 */
@Component
@AllArgsConstructor
public class TaskEventsHandler {

    private final MessageBus messageBus;
    private final SmsNotificator smsNotificator;
    private final EmailNotificator emailNotificator;
    private final UserRepository userRepository;

    @EventListener(TaskCreatedEvent.class)
    public void onTaskCreated(TaskCreatedEvent event) {
        TaskCreated taskCreated = new TaskCreated(event.getTask().getPublicId());

        messageBus.sendMessage(MessageTopics.TASK_CUD, taskCreated);
    }

    @EventListener(TaskClosedEvent.class)
    public void onTaskClosed(TaskClosedEvent event) {
        TaskClosed taskClosed = new TaskClosed(event.getTaskId(), event.getTask().getAssignedTo().getId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskClosed);
    }

    @EventListener(TaskAssignedEvent.class)
    public void onTaskAssigned(TaskAssignedEvent event) {
        TaskAssigned taskAssigned = new TaskAssigned(event.getTaskId(), event.getAssignedTo());
        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskAssigned);

        User assignedUser = userRepository.find(event.getAssignedTo()).orElseThrow();

        String messageText = "The task has been assigned to you. Task name: " + event.getTask().getTitle();

        emailNotificator.notify(assignedUser.getEmail(), messageText);
        smsNotificator.notify(assignedUser.getPhone(), messageText);
    }
}

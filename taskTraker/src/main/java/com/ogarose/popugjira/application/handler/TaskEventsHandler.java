package com.ogarose.popugjira.application.handler;

import com.ogarose.popugjira.application.handler.message.biz.TaskAssignedMessage;
import com.ogarose.popugjira.application.handler.message.biz.TaskClosedMessage;
import com.ogarose.popugjira.application.handler.message.cud.TaskCreatedMessage;
import com.ogarose.popugjira.application.notificator.EmailNotificator;
import com.ogarose.popugjira.application.notificator.SmsNotificator;
import com.ogarose.popugjira.domain.todo.TaskAssignedEvent;
import com.ogarose.popugjira.domain.todo.TaskClosedEvent;
import com.ogarose.popugjira.domain.todo.TaskCreatedEvent;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import com.ogarose.popugjira.infrastructure.messaging.MessageBus;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskEventsHandler {

    private final MessageBus messageBus;
    private final SmsNotificator smsNotificator;
    private final EmailNotificator emailNotificator;
    private final UserRepository userRepository;

    @EventListener(TaskCreatedEvent.class)
    public void onTaskCreated(TaskCreatedEvent event) {
        TaskCreatedMessage taskCreatedMessage = new TaskCreatedMessage(event.getTask().getPublicId());

        messageBus.sendMessage(MessageTopics.TASK_CUD, taskCreatedMessage.toString());
    }

    @EventListener(TaskClosedEvent.class)
    public void onTaskClosed(TaskClosedEvent event) {
        TaskClosedMessage taskClosedMessage = new TaskClosedMessage(event.getTaskId(), event.getTask().getAssignedTo().getId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskClosedMessage.toString());
    }

    @EventListener(TaskAssignedEvent.class)
    public void onTaskAssigned(TaskAssignedEvent event) {
        TaskAssignedMessage taskAssignedMessage = new TaskAssignedMessage(event.getTaskId(), event.getAssignedTo());
        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskAssignedMessage.toString());

        User assignedUser = userRepository.find(event.getAssignedTo()).orElseThrow();

        String messageText = "The task has been assigned to you. Task name: " + event.getTask().getTitle();

        emailNotificator.notify(assignedUser.getEmail(), messageText);
        smsNotificator.notify(assignedUser.getPhone(), messageText);
    }
}

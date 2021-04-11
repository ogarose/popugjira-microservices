package com.ogarose.popugjira.application.handler;

import com.ogarose.popugjira.application.notificator.EmailNotificator;
import com.ogarose.popugjira.application.notificator.SmsNotificator;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import com.ogarose.popugjira.domain.todo.TaskEvent;
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

    @EventListener(TaskEvent.Created.class)
    public void onTaskCreated(TaskEvent.Created event) {
        TaskCreated taskCreated = new TaskCreated(event.getTask().getId().getPublicId());

        messageBus.sendMessage(MessageTopics.TASK_CUD, taskCreated);
    }

    @EventListener(TaskEvent.Closed.class)
    public void onTaskClosed(TaskEvent.Closed event) {
        TaskClosed taskClosed = new TaskClosed(event.getTask().getId().getPublicId(), event.getTask().getAssignToId());

        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskClosed);
    }

    @EventListener(TaskEvent.Assigned.class)
    public void onTaskAssigned(TaskEvent.Assigned event) {
        TaskAssigned taskAssigned = new TaskAssigned(event.getTask().getId().getPublicId(), event.getAssignedTo());
        messageBus.sendMessage(MessageTopics.TASK_BIZ, taskAssigned);

        User assignedUser = userRepository.find(event.getAssignedTo()).orElseThrow();

        String messageText = "The task has been assigned to you. Task name: " + event.getTask().getTitle();

        emailNotificator.notify(assignedUser.getEmail(), messageText);
        smsNotificator.notify(assignedUser.getPhone(), messageText);
    }
}

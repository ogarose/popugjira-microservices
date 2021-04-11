package com.ogarose.popugjira.application.handler.task;

import com.ogarose.popugjira.application.notificator.EmailNotificator;
import com.ogarose.popugjira.application.notificator.SmsNotificator;
import com.ogarose.popugjira.domain.todo.TaskEvent;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendNotificationAfterUserWasAssigned {
    private final UserRepository userRepository;
    private final SmsNotificator smsNotificator;
    private final EmailNotificator emailNotificator;

    @EventListener(TaskEvent.Assigned.class)
    public void handle(TaskEvent.Assigned event) {
        User assignedUser = userRepository.find(event.getAssignedTo()).orElseThrow();

        String messageText = "The task has been assigned to you. Task name: " + event.getTask().getTitle();

        emailNotificator.notify(assignedUser.getEmail(), messageText);
        smsNotificator.notify(assignedUser.getPhone(), messageText);
    }
}

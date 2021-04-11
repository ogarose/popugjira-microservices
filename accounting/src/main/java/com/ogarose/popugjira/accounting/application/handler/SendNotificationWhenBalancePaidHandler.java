package com.ogarose.popugjira.accounting.application.handler;

import com.ogarose.popugjira.accounting.application.notification.EmailNotificator;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SendNotificationWhenBalancePaidHandler {
    private final UserRepository userRepository;
    private final EmailNotificator emailNotificator;

    public void handle(UUID userId, Integer paidSum) {
        User user = userRepository.find(userId).orElseThrow();

        emailNotificator.sendNotification(user.getEmail(), String.format("You salary has been paid to you. Sum: %d", paidSum));
    }
}

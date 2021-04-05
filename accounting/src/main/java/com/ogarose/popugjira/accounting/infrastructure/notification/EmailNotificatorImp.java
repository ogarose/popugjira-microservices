package com.ogarose.popugjira.accounting.infrastructure.notification;

import com.ogarose.popugjira.accounting.application.notification.EmailNotificator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificatorImp implements EmailNotificator {
    @Override
    public void sendNotification(String email, String text) {
        log.info(String.format("---- EMAIL Message ---- EMAIL: %s. TEXT: %s", email, text));
    }
}

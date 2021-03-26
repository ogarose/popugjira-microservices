package com.ogarose.popugjira.infrastructure.notificator;

import com.ogarose.popugjira.application.notificator.EmailNotificator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificatorImp implements EmailNotificator {
    @Override
    public void notify(String email, String message) {
        log.info("Email sent. Email: " + email + ". Message: " + message);
    }
}

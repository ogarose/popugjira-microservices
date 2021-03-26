package com.ogarose.popugjira.infrastructure.notificator;

import com.ogarose.popugjira.application.notificator.SmsNotificator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsNotificatorImp implements SmsNotificator {
    @Override
    public void notify(String phoneNumber, String message) {
        log.info("SMS sent. Phone: " + phoneNumber + ". Message: " + message);
    }
}

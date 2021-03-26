package com.ogarose.popugjira.application.notificator;

public interface SmsNotificator {
    void notify(String phoneNumber, String message);
}

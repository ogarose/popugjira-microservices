package com.ogarose.popugjira.accounting.application.notification;

public interface EmailNotificator {
    void sendNotification(String email, String text);
}

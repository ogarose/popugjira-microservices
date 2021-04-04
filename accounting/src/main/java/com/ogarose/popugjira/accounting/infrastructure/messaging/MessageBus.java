package com.ogarose.popugjira.accounting.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageBus {
    public void sendMessage(String topic, String event) {
        log.info("Sent Event. Topic: " + topic + ". Payload: " + event);
    }
}

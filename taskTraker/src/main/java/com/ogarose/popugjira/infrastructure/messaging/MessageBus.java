package com.ogarose.popugjira.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MessageBus {
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object message) {
        log.info("Sent Event. Topic: " + topic + ". Payload: " + message);

        kafkaTemplate.send(topic, message);
    }
}

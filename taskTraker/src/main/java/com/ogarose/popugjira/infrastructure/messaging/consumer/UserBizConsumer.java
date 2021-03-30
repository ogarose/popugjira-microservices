package com.ogarose.popugjira.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogarose.popugjira.infrastructure.messaging.MessageTopics;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserBizConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = MessageTopics.USER_BIZ, groupId = "tasktraker")
    public void consume(String message) {
        IncomeMessage incomeMessage;
        try {
            incomeMessage = objectMapper.readValue(message, IncomeMessage.class);
            log.info(String.format("$$$$ => Consumed BIZ message: %s", incomeMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        if (incomeMessage == null) {
            return;
        }

        if(incomeMessage.getEventName().equals("UserRoleUpdated")) {
            log.info(String.format("USER ROLE WAS updated %s", incomeMessage.getPayload().get("role").asText()));
        }
    }
}

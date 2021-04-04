package com.ogarose.popugjira.infrastructure.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.auth.biz.UserRoleUpdated;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.USER_BIZ, groupId = "tasktraker")
public class UserBizConsumer {

    private final ObjectMapper objectMapper;

    @KafkaHandler
    public void listener(UserRoleUpdated userRoleUpdated) {
        log.info(String.format("USER ROLE WAS updated %s", userRoleUpdated));
    }
}

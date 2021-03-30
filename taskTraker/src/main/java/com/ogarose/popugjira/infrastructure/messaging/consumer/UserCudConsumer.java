package com.ogarose.popugjira.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogarose.popugjira.domain.user.Role;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.infrastructure.messaging.MessageTopics;
import com.ogarose.popugjira.infrastructure.persistence.repository.todo.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserCudConsumer {

    private final ObjectMapper objectMapper;
    private final UserRepositoryJpa userRepositoryJpa;

    @KafkaListener(topics = MessageTopics.USER_CUD, groupId = "tasktraker")
    public void consume(String message) {
        log.info(String.format("$$$$ => Consumed message: %s", message));
        IncomeMessage incomeMessage;
        try {
            incomeMessage = objectMapper.readValue(message, IncomeMessage.class);
            log.info(String.format("$$$$ => Consumed message: %s", incomeMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        if (incomeMessage == null) {
            return;
        }

        //@todo need to think about DDD and handling CUD events. Where it should be placed
        switch (incomeMessage.getEventName()) {
            case "UserCreated":
                User newUser = new User(
                        UUID.fromString(incomeMessage.getPayload().get("publicId").asText()),
                        incomeMessage.getPayload().get("username").asText(),
                        Role.valueOf(incomeMessage.getPayload().get("role").asText()),
                        incomeMessage.getPayload().get("email").asText(),
                        incomeMessage.getPayload().get("phone").asText()
                );
                userRepositoryJpa.save(newUser);
                break;
            case "UserDeleted":
                userRepositoryJpa.deleteById(UUID.fromString(incomeMessage.getPayload().get("publicId").asText()));
                break;
            case "UserUpdated":
                Optional<User> userToUpdateOption = userRepositoryJpa
                        .findById(
                                UUID.fromString(incomeMessage.getPayload().get("publicId").asText())
                        );

                if(userToUpdateOption.isEmpty()) {
                    log.info(String.format("Can not find a user with id: %s", incomeMessage.getPayload().get("publicId").asText()));
                    return;
                }
                User userToUpdate = userToUpdateOption.get();

                userToUpdate.update(
                        incomeMessage.getPayload().get("username").asText(),
                        Role.valueOf(incomeMessage.getPayload().get("role").asText()),
                        incomeMessage.getPayload().get("email").asText(),
                        incomeMessage.getPayload().get("phone").asText()
                );

                userRepositoryJpa.save(userToUpdate);
        }
    }
}

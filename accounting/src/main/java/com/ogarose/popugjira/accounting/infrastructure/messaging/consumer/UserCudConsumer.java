package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.domain.user.Role;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.accounting.infrastructure.persistence.user.UserRepositoryJpa;
import com.ogarose.popugjira.common.messaging.auth.cud.UserCreated;
import com.ogarose.popugjira.common.messaging.auth.cud.UserDeleted;
import com.ogarose.popugjira.common.messaging.auth.cud.UserUpdated;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.USER_CUD)
public class UserCudConsumer {

    public static final String CONSUMED_MESSAGE_S = "$$$$ => Consumed message: %s";

    private final UserRepositoryJpa userRepositoryJpa;

    @KafkaHandler
    public void listener(UserCreated userCreated) {
        log.info(String.format(CONSUMED_MESSAGE_S, userCreated));

        User newUser = new User(
                userCreated.getPublicId(),
                userCreated.getUsername(),
                Role.valueOf(userCreated.getRole()),
                userCreated.getEmail(),
                userCreated.getPhone()
        );
        userRepositoryJpa.save(newUser);
    }

    @KafkaHandler
    public void listener(UserDeleted userDeleted) {
        log.info(String.format(CONSUMED_MESSAGE_S, userDeleted));

        userRepositoryJpa.deleteById(userDeleted.getPublicId());
    }

    @KafkaHandler
    public void listener(UserUpdated userUpdated) {
        log.info(String.format(CONSUMED_MESSAGE_S, userUpdated));

        Optional<User> userToUpdateOption = userRepositoryJpa
                .findById(userUpdated.getPublicId());

        if (userToUpdateOption.isEmpty()) {
            log.info(String.format("Can not find a user with id: %s", userUpdated.getPublicId()));
            return;
        }
        User userToUpdate = userToUpdateOption.get();

        userToUpdate.update(
                userUpdated.getUsername(),
                Role.valueOf(userUpdated.getRole()),
                userUpdated.getEmail(),
                userUpdated.getPhone()
        );

        userRepositoryJpa.save(userToUpdate);
    }
}

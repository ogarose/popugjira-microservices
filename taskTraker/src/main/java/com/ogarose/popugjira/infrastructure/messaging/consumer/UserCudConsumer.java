package com.ogarose.popugjira.infrastructure.messaging.consumer;

import com.ogarose.popugjira.common.messaging.auth.cud.UserCreated;
import com.ogarose.popugjira.common.messaging.auth.cud.UserDeleted;
import com.ogarose.popugjira.common.messaging.auth.cud.UserUpdated;
import com.ogarose.popugjira.domain.user.Role;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.infrastructure.messaging.MessageTopics;
import com.ogarose.popugjira.infrastructure.persistence.repository.todo.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.USER_CUD, groupId = "tasktraker")
public class UserCudConsumer {

    private final UserRepositoryJpa userRepositoryJpa;

    @KafkaHandler
    public void listener(UserCreated userCreated) {
        log.info(String.format("$$$$ => Consumed message: %s", userCreated));

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
        log.info(String.format("$$$$ => Consumed message: %s", userDeleted));

        userRepositoryJpa.deleteById(userDeleted.getPublicId());
    }

    @KafkaHandler
    public void listener(UserUpdated userUpdated) {
        log.info(String.format("$$$$ => Consumed message: %s", userUpdated));

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

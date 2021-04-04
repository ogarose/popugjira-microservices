package com.ogarose.popugjira.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogarose.popugjira.auth.model.User;
import com.ogarose.popugjira.auth.repository.UserRepositoryJpa;
import com.ogarose.popugjira.auth.service.command.UserCommand;
import com.ogarose.popugjira.auth.service.command.UserCommandMapper;
import com.ogarose.popugjira.auth.service.message.MessageBus;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.auth.biz.UserRoleUpdated;
import com.ogarose.popugjira.common.messaging.auth.cud.UserCreated;
import com.ogarose.popugjira.common.messaging.auth.cud.UserDeleted;
import com.ogarose.popugjira.common.messaging.auth.cud.UserUpdated;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepositoryJpa userRepositoryJpa;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageBus messageBus;
    private final UserCommandMapper userCommandMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepositoryJpa.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return userOptional.get();
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        userRepositoryJpa.findAll().forEach(userList::add);

        return userList;
    }

    public User getById(UUID id) {
        return userRepositoryJpa.findById(id).orElseThrow();
    }

    public void createUser(UserCommand userCommand) throws JsonProcessingException {
        User userToSave = userCommandMapper.fromCommand(userCommand);

        saveUser(userToSave);

        UserCreated event = new UserCreated(
                userToSave.getId(),
                userToSave.getUsername(),
                userToSave.getRole().name(),
                userToSave.getEmail(),
                userToSave.getPhone()
        );

        messageBus.sendMessage(MessageTopics.USER_CUD, event);
    }

    public void updateUser(UserCommand userCommand) throws JsonProcessingException {
        User user = getById(userCommand.getId());
        user.setUsername(userCommand.getUsername());
        user.setPhone(userCommand.getPhone());
        user.setEmail(userCommand.getEmail());
        UserRoleUpdated userRoleUpdated = null;
        if (!user.getRole().equals(userCommand.getRole())) {
            userRoleUpdated = new UserRoleUpdated(user.getId(), userCommand.getRole().name());
        }
        user.setRole(userCommand.getRole());

        Boolean hasPassword = userCommand.getPassword().length() > 0;
        if (Boolean.TRUE.equals(hasPassword)) {
            user.setPassword(userCommand.getPassword());
        }
        saveUser(user, hasPassword);

        UserUpdated userUpdated = new UserUpdated(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                user.getEmail(),
                user.getPhone()
        );
        messageBus.sendMessage(MessageTopics.USER_CUD, userUpdated);
        if (userRoleUpdated != null) {
            messageBus.sendMessage(MessageTopics.USER_BIZ, userRoleUpdated);
        }
    }

    public boolean saveUser(User user) {
        return saveUser(user, true);
    }

    public boolean saveUser(User user, Boolean changePassword) {
        if (Boolean.TRUE.equals(changePassword)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepositoryJpa.save(user);
        return true;
    }

    public void delete(User user) throws JsonProcessingException {
        userRepositoryJpa.delete(user);

        UserDeleted event = new UserDeleted(user.getId());
        messageBus.sendMessage(MessageTopics.USER_CUD, event);
    }
}

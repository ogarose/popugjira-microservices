package com.ogarose.popugjira.infrastructure.bootstrap;

import com.ogarose.popugjira.domain.user.Role;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        Optional<User> testUser = userRepository.findByName("test_user");
//        if(testUser.isEmpty()) {
//            User defaultUser = new User(
//                    UUID.randomUUID(),
//                    "test_user",
//                    Role.ROLE_EMPLOYEE,
//                    "test_user@test.io",
//                    "+368 897 5558"
//            );
//            userRepository.save(defaultUser);
//        }
    }
}

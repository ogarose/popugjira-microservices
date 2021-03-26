package com.ogarose.popugjira.auth.bootstrap;

import com.ogarose.popugjira.auth.model.Roles;
import com.ogarose.popugjira.auth.model.User;
import com.ogarose.popugjira.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            userService.loadUserByUsername("employee_2");
        } catch (UsernameNotFoundException e) {
            User defaultUser = new User();
            defaultUser.setRole(Roles.ROLE_EMPLOYEE);
            defaultUser.setUsername("employee_2");
            defaultUser.setPassword("pass");
            defaultUser.setEmail("employee_2@test.te");
            defaultUser.setPhone("+375 78 778 78 78");

            userService.saveUser(defaultUser);
        }
        try {
            userService.loadUserByUsername("admin_2");
        } catch (UsernameNotFoundException e) {
            User defaultUser = new User();
            defaultUser.setRole(Roles.ROLE_ADMIN);
            defaultUser.setUsername("admin_2");
            defaultUser.setPassword("pass");
            defaultUser.setEmail("admin_2@test.te");
            defaultUser.setPhone("+375 88 888 8888");

            userService.saveUser(defaultUser);
        }
    }
}

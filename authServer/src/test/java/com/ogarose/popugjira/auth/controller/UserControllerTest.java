package com.ogarose.popugjira.auth.controller;

import com.ogarose.popugjira.auth.model.Roles;
import com.ogarose.popugjira.auth.model.User;
import com.ogarose.popugjira.auth.repository.UserRepositoryJpa;
import com.ogarose.popugjira.auth.service.message.MessageBus;
import com.ogarose.popugjira.common.messaging.auth.cud.UserCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @MockBean
    private MessageBus messageBus;

    @BeforeEach
    public void setUp() {
        User defaultUser = new User();
        defaultUser.setRole(Roles.ROLE_EMPLOYEE);
        defaultUser.setUsername("test_2");
        defaultUser.setPassword("pass");
        defaultUser.setEmail("test_2@test.te");
        defaultUser.setPhone("+375 78 772 87 87");

        userRepositoryJpa.save(defaultUser);
    }

    @Test
    public void showAllUsersList() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test_2@test.te")));
    }

    @Test
    public void addNewUser() throws Exception {
        this.mockMvc.perform(post("/users")
                .param("username", "test_new")
                .param("password", "pass")
                .param("email", "test_new@tit.vt")
                .param("phone", "878979")
                .param("role", Roles.ROLE_ADMIN.name())
        )
                .andExpect(status().is3xxRedirection());

        Optional<User> newUser = userRepositoryJpa.findByUsername("test_new");
        assertTrue(newUser.isPresent());

        ArgumentCaptor<UserCreated> argument = ArgumentCaptor.forClass(UserCreated.class);

        Mockito.verify(messageBus).sendMessage(Mockito.eq("auth.cud.user"), argument.capture());
        assertEquals("878979", argument.getValue().getPhone());
        assertEquals("test_new", argument.getValue().getUsername());
    }
}

package com.ogarose.popugjira.presentation.controller;

import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import com.ogarose.popugjira.domain.todo.Status;
import com.ogarose.popugjira.domain.todo.Task;
import com.ogarose.popugjira.domain.todo.TaskRepository;
import com.ogarose.popugjira.domain.user.Role;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import com.ogarose.popugjira.infrastructure.messaging.MessageBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private MessageBus messageBus;

    @Test
    void createNewTask() throws Exception {
        this.mockMvc.perform(post("/task")
                .param("title", "a new task")
        )
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<TaskCreated> argument = ArgumentCaptor.forClass(TaskCreated.class);
        Mockito.verify(messageBus).sendMessage(Mockito.eq("tracker.cud.task"), argument.capture());

        Optional<Task> newTask = taskRepository.findAllOpened()
                .stream()
                .filter(task -> task.getPublicId().equals(argument.getValue().getId()))
                .findFirst();
        Assertions.assertTrue(newTask.isPresent());
    }

    @Test
    @Transactional
    void reasignAllAssignAllExistedTaskToUsersAndSendTaskAssignedMessage() throws Exception {
        User admin1 = new User(UUID.randomUUID(), "admin 1", Role.ROLE_ADMIN, "email@tit.vt", "12212");
        User employee1 = new User(UUID.randomUUID(), "employee 1", Role.ROLE_EMPLOYEE, "email_emp@tit.vt", "585992627");
        userRepository.save(admin1);
        userRepository.save(employee1);

        Task task1 = new Task(UUID.randomUUID(), "string");
        Task task2 = new Task(UUID.randomUUID(), "string");
        Task task3 = new Task(UUID.randomUUID(), "string");
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);


        this.mockMvc.perform(get("/task/reassign-all")
        )
                .andExpect(status().is3xxRedirection());

        Assertions.assertNotNull(taskRepository.find(task1.getId()).orElseThrow().getAssignedTo());
        Assertions.assertNotNull(taskRepository.find(task2.getId()).orElseThrow().getAssignedTo());
        Assertions.assertNotNull(taskRepository.find(task3.getId()).orElseThrow().getAssignedTo());

        ArgumentCaptor<TaskAssigned> argument = ArgumentCaptor.forClass(TaskAssigned.class);
        Mockito.verify(messageBus, Mockito.times(3)).sendMessage(Mockito.eq("tracker.cud.task"), argument.capture());
    }


    @Test
    @Transactional
    void closeTaskSetTaskStatusToClosedAndSendMessage() throws Exception {
        User admin1 = new User(UUID.randomUUID(), "admin 1", Role.ROLE_ADMIN, "email@tit.vt", "12212");
        userRepository.save(admin1);

        Task task1 = new Task(UUID.randomUUID(), "string");
        taskRepository.save(task1);
        task1.assignTo(admin1);
        taskRepository.save(task1);


        this.mockMvc.perform(get(String.format("/task/%s/close", task1.getId()))
        )
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(Status.CLOSED, taskRepository.find(task1.getId()).orElseThrow().getStatus());

        ArgumentCaptor<TaskClosed> argument = ArgumentCaptor.forClass(TaskClosed.class);
        Mockito.verify(messageBus, Mockito.times(1)).sendMessage(Mockito.eq("tracker.cud.task"), argument.capture());
    }
}

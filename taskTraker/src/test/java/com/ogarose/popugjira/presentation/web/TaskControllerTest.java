package com.ogarose.popugjira.presentation.web;

import com.ogarose.popugjira.common.messaging.traker.biz.TaskAssigned;
import com.ogarose.popugjira.common.messaging.traker.biz.TaskClosed;
import com.ogarose.popugjira.common.messaging.traker.cud.TaskCreated;
import com.ogarose.popugjira.domain.todo.*;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
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
    private TaskExtractor taskExtractor;

    @Autowired
    private TaskPersister taskPersister;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskIdGenerator taskIdGenerator;

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

        Optional<Task> newTask = taskExtractor.findAllOpened()
                .stream()
                .filter(task -> task.getId().getPublicId().equals(argument.getValue().getId()))
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

        Task taskEntity1 = Task.createNew(taskIdGenerator, new TaskTitle("asldkjlkj"));
        Task taskEntity2 = Task.createNew(taskIdGenerator, new TaskTitle("as23fldkjlkj"));
        Task taskEntity3 = Task.createNew(taskIdGenerator, new TaskTitle("asldkjlkj tftf"));
        taskPersister.save(taskEntity1);
        taskPersister.save(taskEntity2);
        taskPersister.save(taskEntity3);


        this.mockMvc.perform(get("/task/reassign-all")
        )
                .andExpect(status().is3xxRedirection());

        Assertions.assertNotNull(taskExtractor.find(taskEntity1.getId().getInternalId()).orElseThrow().getAssignToId());
        Assertions.assertNotNull(taskExtractor.find(taskEntity2.getId().getInternalId()).orElseThrow().getAssignToId());
        Assertions.assertNotNull(taskExtractor.find(taskEntity3.getId().getInternalId()).orElseThrow().getAssignToId());

        ArgumentCaptor<TaskAssigned> argument = ArgumentCaptor.forClass(TaskAssigned.class);
        Mockito.verify(messageBus, Mockito.times(3)).sendMessage(Mockito.eq("tracker.cud.task"), argument.capture());
    }

    @Test
    @Transactional
    void closeTaskSetTaskStatusToClosedAndSendMessage() throws Exception {
        User admin1 = new User(UUID.randomUUID(), "admin 1", Role.ROLE_ADMIN, "email@tit.vt", "12212");
        userRepository.save(admin1);

        Task taskEntity1 = Task.createNew(taskIdGenerator, new TaskTitle("asldkjlkj"));
        taskPersister.save(taskEntity1);
        taskEntity1.assignTo(admin1);
        taskPersister.save(taskEntity1);


        this.mockMvc.perform(get(String.format("/task/%s/close", taskEntity1.getId().getInternalId().value()))
        )
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(Status.CLOSED, taskExtractor.find(taskEntity1.getId().getInternalId()).orElseThrow().getStatus());

        ArgumentCaptor<TaskClosed> argument = ArgumentCaptor.forClass(TaskClosed.class);
        Mockito.verify(messageBus, Mockito.times(1)).sendMessage(Mockito.eq("tracker.cud.task"), argument.capture());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {
        @Bean
        @Primary
        public TaskIdGenerator taskIdGenerator() {
            return new TaskIdGenerator() {
                private Integer currentId = 1;

                @Override
                public TaskId generate() {
                    return new TaskId(
                            new TaskId.TaskInternalId(++currentId),
                            UUID.randomUUID()
                    );
                }
            };
        }
    }
}

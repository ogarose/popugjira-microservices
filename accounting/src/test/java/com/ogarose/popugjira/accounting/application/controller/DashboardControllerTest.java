package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.application.notification.EmailNotificator;
import com.ogarose.popugjira.accounting.domain.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
public class DashboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @MockBean
    EmailNotificator emailNotificator;

    @Test
    void usersBalanceIsResetTo0IfItIsBiggerThan0AndTransactionRecordIsAddedAndEmailIsSent() throws Exception {
        User admin = new User(UUID.randomUUID(), "admin21", Role.ROLE_ADMIN, "someEmai2l@tut.vt", "23089233");
        admin.increaseBalance(323);
        userRepository.save(admin);

        this.mockMvc.perform(get("/pay-day-balance")
        )
                .andExpect(status().is3xxRedirection());

                Thread.sleep(4000);

        User updatedUser = userRepository.find(admin.getId()).orElseThrow();
        Assertions.assertEquals(updatedUser.getBalance(), 0);

        List<Transaction> allOfDayByUser = transactionRepository.findAllOfDayByUser(admin);
        Assertions.assertEquals(1, allOfDayByUser.size());

        Transaction salaryTransaction = allOfDayByUser.get(0);
        Assertions.assertEquals(salaryTransaction.getCredit(), 323);

        Mockito.verify(emailNotificator).sendNotification(Mockito.eq(admin.getEmail()), Mockito.anyString());
    }
}

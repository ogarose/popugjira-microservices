package com.ogarose.popugjira.infrastructure.persistence.todo;

import com.ogarose.popugjira.domain.todo.TaskId;
import com.ogarose.popugjira.domain.todo.TaskIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskIdDBGenerator implements TaskIdGenerator {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public TaskId generate() {
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            BigInteger nextId = (BigInteger) session.createNativeQuery("SELECT AUTO_INCREMENT AS ai FROM information_schema.tables WHERE table_name = 'task' AND table_schema = DATABASE()")
                    .getSingleResult();

            return nextId == null
                    ? new TaskId(new TaskId.TaskInternalId(1), UUID.randomUUID())
                    : new TaskId(new TaskId.TaskInternalId(nextId.intValue()), UUID.randomUUID());
        } catch (NoResultException e) {
            return null;
        } finally {
            if (session.isOpen()) session.close();
        }
    }
}

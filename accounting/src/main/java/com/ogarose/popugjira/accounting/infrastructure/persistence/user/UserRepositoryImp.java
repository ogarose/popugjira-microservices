package com.ogarose.popugjira.accounting.infrastructure.persistence.user;

import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImp implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private EntityManagerFactory entityManagerFactory;


    @Override
    public Optional<User> find(UUID id) {
        return userRepositoryJpa.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepositoryJpa.findByName(name);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepositoryJpa.findAll();
    }

    @Override
    public Integer getManagerEarnToday() {
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            BigDecimal earnSum = (BigDecimal)session.createNativeQuery("select sum(sub.sum_credit) as today_earn from (select task_id, sum(credit) as sum_credit, sum(debit) as sum_debit from transaction where task_id is not null and date(created_at) = date(now()) group by task_id having sum_debit is null) as sub")
                    .getSingleResult();

            return Math.abs(earnSum.intValue());
        }
        catch (NoResultException e){
            return null;
        }
        finally {
            if(session.isOpen()) session.close();
        }
    }

    @Override
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }
}

package com.ogarose.popugjira.accounting.infrastructure.persistence.user;

import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionRepositoryImp implements TransactionRepository {
    private final TransactionRepositoryJpa transactionRepositoryJpa;

    @Override
    public List<Transaction> findAllOfDayByUser(User userId) {
        return transactionRepositoryJpa.findAllOfDayByUser(userId.getId());
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepositoryJpa.save(transaction);
    }
}

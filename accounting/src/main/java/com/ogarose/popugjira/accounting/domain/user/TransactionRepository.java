package com.ogarose.popugjira.accounting.domain.user;

import java.util.List;

public interface TransactionRepository {
    public List<Transaction> findAllOfDayByUser(User userId);

    public Transaction save(Transaction transaction);
}

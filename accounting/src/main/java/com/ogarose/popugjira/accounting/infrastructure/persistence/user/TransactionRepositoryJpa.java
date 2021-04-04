package com.ogarose.popugjira.accounting.infrastructure.persistence.user;

import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryJpa extends CrudRepository<Transaction, UUID> {
    List<Transaction> findAllByUserOrderByCreatedAt(User id);
}

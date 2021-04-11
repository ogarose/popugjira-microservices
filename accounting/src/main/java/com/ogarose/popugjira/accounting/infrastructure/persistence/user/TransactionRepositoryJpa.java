package com.ogarose.popugjira.accounting.infrastructure.persistence.user;

import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryJpa extends CrudRepository<Transaction, UUID> {
    List<Transaction> findAllByUserOrderByCreatedAt(User id);

    @Query(value = "SELECT transaction.* FROM transaction WHERE transaction.user_id = :userId AND DATE(transaction.created_at) = DATE(NOW())", nativeQuery = true)
    List<Transaction> findAllOfDayByUser(@Param("userId") UUID id);
}

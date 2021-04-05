package com.ogarose.popugjira.accounting.infrastructure.persistence.user;

import com.ogarose.popugjira.accounting.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends CrudRepository<User, UUID> {
    Optional<User> findByName(String name);

    @Query("SELECT u FROM User u WHERE u.balance > 0")
    List<User> findAllWithPositiveBalance();
}

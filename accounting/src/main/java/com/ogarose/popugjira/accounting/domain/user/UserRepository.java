package com.ogarose.popugjira.accounting.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> find(UUID id);
    Optional<User> findByName(String name);
    List<User> findAll();

    Integer getManagerEarnToday();
    List<User> findAllWithPositiveBalance();

    User save(User user);
}

package com.ogarose.popugjira.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> find(UUID id);
    Optional<User> findByName(String name);
    List<User> findAll();

    User save(User user);
}

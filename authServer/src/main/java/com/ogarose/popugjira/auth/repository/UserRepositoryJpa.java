package com.ogarose.popugjira.auth.repository;

import com.ogarose.popugjira.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends CrudRepository<User, UUID> {
    @Override
    Optional<User> findById(UUID uuid);

    Optional<User> findByUsername(String name);
}

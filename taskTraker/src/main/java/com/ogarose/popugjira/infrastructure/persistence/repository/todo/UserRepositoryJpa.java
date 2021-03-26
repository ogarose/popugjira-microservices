package com.ogarose.popugjira.infrastructure.persistence.repository.todo;

import com.ogarose.popugjira.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends CrudRepository<User, UUID> {
    Optional<User> findByName(String name);
}

package com.ogarose.popugjira.infrastructure.persistence.user;

import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImp implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;

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
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }
}

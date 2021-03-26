package com.ogarose.popugjira.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> find(UUID id);
    Optional<User> findByName(String name);
    List<User> findAll();

    /**
     * @todo need to look into Transactional? Save Once per app service.
     */
    User save(User user);
}

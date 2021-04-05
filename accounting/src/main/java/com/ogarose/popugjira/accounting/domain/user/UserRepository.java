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

    /**
     * @todo need to look into Transactional? Save Once per app service.
     */
    User save(User user);
}

package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.User;

public interface UserRepository {
    User findById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    void delete(User user);
}

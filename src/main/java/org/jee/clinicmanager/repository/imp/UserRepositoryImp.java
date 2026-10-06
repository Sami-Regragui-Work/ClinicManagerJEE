package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jee.clinicmanager.model.User;
import org.jee.clinicmanager.repository.UserRepository;

public class UserRepositoryImp implements UserRepository {
    private final EntityManager em;

    public UserRepositoryImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return em.createQuery("SELECT COUNT(*) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }

    @Override
    public void delete(User user) {
        if (em.contains(user)) {
            em.remove(user);
        } else {
            em.remove(em.merge(user));
        }
    }
}

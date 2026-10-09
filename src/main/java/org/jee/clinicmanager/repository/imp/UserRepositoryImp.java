package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jee.clinicmanager.model.User;
import org.jee.clinicmanager.repository.UserRepository;

public class UserRepositoryImp extends BaseRepositoryImp implements UserRepository {
    public UserRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public User findById(Long id) {
        return this.em.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        try {
            return this.em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.em.createQuery("SELECT COUNT(*) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            this.em.persist(user);
            return user;
        }
        return this.em.merge(user);
    }

    @Override
    public void delete(User user) {
        if (this.em.contains(user)) this.em.remove(user);
        else this.em.remove(this.em.merge(user));
    }
}

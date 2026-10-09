package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;

public class BaseRepositoryImp {
    protected final EntityManager em;

    public BaseRepositoryImp(EntityManager em) {
        this.em = em;
    }
}

package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import org.jee.clinicmanager.model.Specialty;
import org.jee.clinicmanager.repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyRepositoryImp extends BaseRepositoryImp implements SpecialtyRepository {

    public SpecialtyRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public List<Specialty> findAll() {
        return this.em.createQuery("SELECT s FROM Specialty s", Specialty.class)
                .getResultList();
    }
}

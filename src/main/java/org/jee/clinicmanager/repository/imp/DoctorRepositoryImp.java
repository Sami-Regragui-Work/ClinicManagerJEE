package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import org.jee.clinicmanager.model.Doctor;
import org.jee.clinicmanager.repository.DoctorRepository;

import java.util.List;

public class DoctorRepositoryImp extends BaseRepositoryImp implements DoctorRepository {
    public DoctorRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public List<Doctor> findAll() {
        return this.em.createQuery("SELECT d FROM Doctor d", Doctor.class)
                .getResultList();
    }

    @Override
    public Doctor findById(Long id) {
        return this.em.find(Doctor.class, id);
    }
}

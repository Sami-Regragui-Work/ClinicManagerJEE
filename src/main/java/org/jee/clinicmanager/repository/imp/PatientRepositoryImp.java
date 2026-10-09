package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import org.jee.clinicmanager.model.Patient;
import org.jee.clinicmanager.repository.PatientRepository;

public class PatientRepositoryImp extends BaseRepositoryImp implements PatientRepository {
    public PatientRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public Patient findById(Long id) {
        return this.em.find(Patient.class, id);
    }
}

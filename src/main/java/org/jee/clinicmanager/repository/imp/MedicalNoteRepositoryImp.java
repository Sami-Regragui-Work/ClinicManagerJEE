package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jee.clinicmanager.model.MedicalNote;
import org.jee.clinicmanager.repository.MedicalNoteRepository;

public class MedicalNoteRepositoryImp extends BaseRepositoryImp implements MedicalNoteRepository {

    public MedicalNoteRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public MedicalNote findById(Long id) {
        return this.em.find(MedicalNote.class, id);
    }

    @Override
    public MedicalNote findByAppointmentId(Long appointmentId) {
        try {
            return this.em.createQuery("SELECT m FROM MedicalNote m WHERE m.appointment.id = :id", MedicalNote.class)
                    .setParameter("id", appointmentId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public MedicalNote save(MedicalNote medicalNote) {
        if (medicalNote.getId() == null) {
            this.em.persist(medicalNote);
            return medicalNote;
        }
        return this.em.merge(medicalNote);
    }

    @Override
    public void delete(MedicalNote medicalNote) {
        if (this.em.contains(medicalNote)) this.em.remove(medicalNote);
        else this.em.remove(this.em.merge(medicalNote));
    }
}

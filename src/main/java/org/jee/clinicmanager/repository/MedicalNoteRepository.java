package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.MedicalNote;

public interface MedicalNoteRepository {
    MedicalNote findById(Long id);
    MedicalNote findByAppointmentId(Long appointmentId);
    MedicalNote save(MedicalNote medicalNote);
    void delete(MedicalNote medicalNote);
}

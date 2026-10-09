package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.Patient;

public interface PatientRepository {
    Patient findById(Long id);
}

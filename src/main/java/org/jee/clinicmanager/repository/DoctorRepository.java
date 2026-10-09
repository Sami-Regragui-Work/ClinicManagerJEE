package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> findAll();
    Doctor findById(Long id);
}

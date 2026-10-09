package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.Specialty;

import java.util.List;

public interface SpecialtyRepository {
    List<Specialty> findAll();
}

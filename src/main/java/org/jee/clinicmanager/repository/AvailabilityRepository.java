package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository {
    Availability findById(Long id);
    List<Availability> findByDoctorId(Long doctorId);
    List<Availability> findByDoctorIdAndFutureDate(Long doctorId, LocalDate startingDate);
    Availability save(Availability availability);
    void delete(Availability availability);
}

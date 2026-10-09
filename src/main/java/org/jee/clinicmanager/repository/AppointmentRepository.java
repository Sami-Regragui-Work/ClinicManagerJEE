package org.jee.clinicmanager.repository;

import org.jee.clinicmanager.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {
    List<Appointment> findByPatientId(Long patientId);
    Appointment findById(Long id);
    Appointment save(Appointment appointment);
    boolean existsByDoctorAndTimeRange(Long doctorId, LocalDate day, LocalTime slotStartingTime, LocalTime slotEndingTime);
}

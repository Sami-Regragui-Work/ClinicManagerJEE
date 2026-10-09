package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentRepositoryImp extends BaseRepositoryImp implements AppointmentRepository {
    public AppointmentRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return this.em.createQuery("SELECT a FROM Appointment a WHERE a.patient.id = :id", Appointment.class)
                .setParameter("id", patientId)
                .getResultList();
    }

    @Override
    public Appointment findById(Long id) {
        return this.em.find(Appointment.class, id);
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            this.em.persist(appointment);
            return appointment;
        }
        return this.em.merge(appointment);
    }

    @Override
    public boolean existsByDoctorAndTimeRange(Long doctorId, LocalDate day, LocalTime slotStartingTime, LocalTime slotEndingTime) {
        return !this.em.createQuery("SELECT a FROM Appointment a WHERE a.doctor.id = :id AND a.date = :day AND a.startTime BETWEEN :startTime AND :endTime", Appointment.class)
                .setParameter("id", doctorId)
                .setParameter("day", day)
                .setParameter("startTime", slotStartingTime)
                .setParameter("endTime", slotEndingTime)
                .getResultList().isEmpty();
    }
}

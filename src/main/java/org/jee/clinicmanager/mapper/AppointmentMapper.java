package org.jee.clinicmanager.mapper;

import org.jee.clinicmanager.dto.response.AppointmentDetailDTO;
import org.jee.clinicmanager.dto.response.AppointmentListItemDTO;
import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.model.Doctor;
import org.jee.clinicmanager.model.MedicalNote;
import org.jee.clinicmanager.model.Patient;
import org.jee.clinicmanager.util.AppointmentRules;

import java.time.LocalDateTime;

public class AppointmentMapper {
    private AppointmentMapper() {}

    public static AppointmentListItemDTO toListItem(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        String doctorName = AppointmentRules.safeConcat(doctor.getFirstName(), doctor.getLastName());

        String specialty = doctor.getDepartment() != null ? doctor.getDepartment().getSpecialty().getTitle() : null;

        return new AppointmentListItemDTO(
                appointment.getId(),
                doctorName,
                specialty,
                LocalDateTime.of(appointment.getDate(), appointment.getStartTime()),
                appointment.getType().toString(),
                appointment.getStatus().toString(),
                AppointmentRules.canCancel(appointment)
        );
    }

    public static AppointmentDetailDTO toDetail(Appointment appointment, MedicalNote medicalNote, boolean canReschedule) {
        Patient patient = appointment.getPatient();
        String patientName = AppointmentRules.safeConcat(patient.getFirstName(), patient.getLastName());

        Doctor doctor = appointment.getDoctor();
        String doctorName = AppointmentRules.safeConcat(doctor.getFirstName(), doctor.getLastName());

        return new AppointmentDetailDTO(
                appointment.getId(),
                patientName,
                doctorName,
                LocalDateTime.of(appointment.getDate(), appointment.getStartTime()),
                appointment.getType().toString(),
                appointment.getStatus().toString(),
                appointment.getReason(),
                AppointmentRules.canCancel(appointment),
                canReschedule,
                medicalNote != null
        );
    }
}

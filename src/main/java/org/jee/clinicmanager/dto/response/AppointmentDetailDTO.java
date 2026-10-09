package org.jee.clinicmanager.dto.response;

import java.time.LocalDateTime;

public class AppointmentDetailDTO {
    private final Long id;
    private final String patientFullName;
    private final String doctorFullName;
    private final LocalDateTime dateStartingTime;
    private final String typeLabel;
    private final String statusLabel;
    private final String reason;
    private final boolean canCancel;
    private final boolean canReschedule;
    private final boolean hasMedicalNote;

    public AppointmentDetailDTO(Long id, String patientFullName, String doctorFullName, LocalDateTime dateStartingTime, String typeLabel, String statusLabel, String reason, boolean canCancel, boolean canReschedule, boolean hasMedicalNote) {
        this.id = id;
        this.patientFullName = patientFullName;
        this.doctorFullName = doctorFullName;
        this.dateStartingTime = dateStartingTime;
        this.typeLabel = typeLabel;
        this.statusLabel = statusLabel;
        this.reason = reason;
        this.canCancel = canCancel;
        this.canReschedule = canReschedule;
        this.hasMedicalNote = hasMedicalNote;
    }

    public Long getId() {
        return id;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public LocalDateTime getDateStartingTime() {
        return dateStartingTime;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public String getReason() {
        return reason;
    }

    public boolean canCancel() {
        return canCancel;
    }

    public boolean canReschedule() {
        return canReschedule;
    }

    public boolean hasMedicalNote() {
        return hasMedicalNote;
    }
}

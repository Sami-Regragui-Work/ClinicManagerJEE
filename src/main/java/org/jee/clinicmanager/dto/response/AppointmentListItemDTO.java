package org.jee.clinicmanager.dto.response;

import java.time.LocalDateTime;

public class AppointmentListItemDTO {
    private final Long id;
    private final String doctorFullName;
    private final String specialtyName;
    private final LocalDateTime dateTime;
    private final String typeLabel;
    private final String statusLabel;
    private final boolean isCancelable;

    public AppointmentListItemDTO(Long id, String doctorFullName, String specialtyName, LocalDateTime dateTime, String typeLabel, String statusLabel, boolean isCancelable) {
        this.id = id;
        this.doctorFullName = doctorFullName;
        this.specialtyName = specialtyName;
        this.dateTime = dateTime;
        this.typeLabel = typeLabel;
        this.statusLabel = statusLabel;
        this.isCancelable = isCancelable;
    }

    public Long getId() {
        return id;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public boolean isCancelable() {
        return isCancelable;
    }
}

package org.jee.clinicmanager.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.jee.clinicmanager.model.enums.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookAppointmentDTO {
    @NotNull(message = "Time slot is required")
    private Long slotId;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Attempt to make an appointment in the past")
    private LocalDate date;

    @NotNull(message = "Starting hour is required")
    private LocalTime startingTime;

    @NotNull
    private AppointmentType type;

    private String reason;

    public BookAppointmentDTO(Long slotId, LocalDate date, LocalTime startingTime, AppointmentType type, String reason) {
        this.slotId = slotId;
        this.date = date;
        this.startingTime = startingTime;
        this.type = type;
        this.reason = reason;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

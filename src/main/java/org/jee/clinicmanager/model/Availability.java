package org.jee.clinicmanager.model;

import jakarta.persistence.*;
import org.jee.clinicmanager.model.enums.AvailabilityStatus;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private DayOfWeek day;
    private LocalTime startingHour;
    private LocalTime endingHour;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;
    private LocalDateTime expiresAt;

    public Availability() {
    }

    public Availability(Doctor doctor, DayOfWeek day, LocalTime startingHour, LocalTime endingHour, AvailabilityStatus status, LocalDateTime expiresAt) {
        this.doctor = doctor;
        this.day = day;
        this.startingHour = startingHour;
        this.endingHour = endingHour;
        this.status = status;
        this.expiresAt = expiresAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(LocalTime startingHour) {
        this.startingHour = startingHour;
    }

    public LocalTime getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(LocalTime endingHour) {
        this.endingHour = endingHour;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}

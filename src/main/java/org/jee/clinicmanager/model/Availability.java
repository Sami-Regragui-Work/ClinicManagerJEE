package org.jee.clinicmanager.model;

import jakarta.persistence.*;
import org.jee.clinicmanager.model.enums.AvailabilityStatus;

import java.time.LocalDate;
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

    private LocalDate day;
    private LocalTime startingTime;
    private LocalTime endingTime;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;
    private LocalDateTime expiresAt;

    public Availability() {
    }

    public Availability(Doctor doctor, LocalDate day, LocalTime startingTime, LocalTime endingTime, AvailabilityStatus status, LocalDateTime expiresAt) {
        this.doctor = doctor;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingHour) {
        this.startingTime = startingHour;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalTime endingHour) {
        this.endingTime = endingHour;
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

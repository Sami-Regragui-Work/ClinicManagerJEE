package org.jee.clinicmanager.model;

import jakarta.persistence.*;
import org.jee.clinicmanager.model.enums.MedicalNoteStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_notes")
public class MedicalNote {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String diagnostic;

    private String content;

    @Column(updatable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private MedicalNoteStatus status;

    @PrePersist
    void onCreate() {
        this.date = LocalDateTime.now();
    }

    public MedicalNote() {
    }

    public MedicalNote(Appointment appointment, String diagnostic, String content, MedicalNoteStatus status) {
        this.appointment = appointment;
        this.diagnostic = diagnostic;
        this.content = content;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDiagnostic(String diagnostic) {
        if (!this.status.equals(MedicalNoteStatus.VALIDATED))
            this.diagnostic = diagnostic;
    }

    public void setContent(String content) {
        if (!this.status.equals(MedicalNoteStatus.VALIDATED))
            this.content = content;
    }

    public MedicalNoteStatus getStatus() {
        return status;
    }

    public void setStatus(MedicalNoteStatus status) {
        if (!this.status.equals(MedicalNoteStatus.VALIDATED))
            this.status = status;
    }
}

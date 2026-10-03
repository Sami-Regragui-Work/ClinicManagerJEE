package org.jee.clinicmanager.model;

import jakarta.persistence.*;
import org.jee.clinicmanager.model.enums.PatientBloodType;
import org.jee.clinicmanager.model.enums.PatientGender;
import org.jee.clinicmanager.model.enums.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends User {
    private String cin;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PatientGender gender;
    private String address;

    @Enumerated(EnumType.STRING)
    private PatientBloodType bloodType;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String email, String phone, String password, boolean isActive, String cin, LocalDate birthDate, PatientGender gender, String address, PatientBloodType bloodType, List<Appointment> appointments) {
        super(firstName, lastName, email, phone, password, UserRole.PATIENT, isActive);
        this.cin = cin;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.bloodType = bloodType;
        this.appointments = appointments == null ? new ArrayList<>() : appointments;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PatientGender getGender() {
        return gender;
    }

    public void setGender(PatientGender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PatientBloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(PatientBloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}

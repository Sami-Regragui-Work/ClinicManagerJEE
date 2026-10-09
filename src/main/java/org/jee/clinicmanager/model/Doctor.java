package org.jee.clinicmanager.model;

import jakarta.persistence.*;
import org.jee.clinicmanager.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends User {
    @Column(unique = true, nullable = false)
    private String matricule;
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Departement department;

    @OneToMany(mappedBy = "doctor")
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String email, String phone, String password, UserRole role, boolean isActive, String matricule, String title, Departement department, List<Availability> availabilities, List<Appointment> appointments) {
        super(firstName, lastName, email, phone, password, role, isActive);
        this.matricule = matricule;
        this.title = title;
        this.department = department;
        this.availabilities = availabilities == null ? new ArrayList<>() : availabilities;
        this.appointments = appointments == null ? new ArrayList<>() : appointments;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Departement getDepartment() {
        return department;
    }

    public void setDepartment(Departement department) {
        this.department = department;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void addAvailability(Availability availability) {
        this.availabilities.add(availability);
    }
}

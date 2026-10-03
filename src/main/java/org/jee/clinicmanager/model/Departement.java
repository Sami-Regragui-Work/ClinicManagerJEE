package org.jee.clinicmanager.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Departement {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    public Departement() {
    }

    public Departement(String title, List<Doctor> doctors) {
        this.title = title;
        this.doctors = doctors == null ? new ArrayList<Doctor>() : doctors;
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
}

package org.jee.clinicmanager.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "specialty")
    private List<Departement> departements;

    public Specialty() {
    }

    public Specialty(String title, List<Departement> departements) {
        this.title = title;
        this.departements = departements == null ? new ArrayList<>() : departements;
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

    public List<Departement> getDepartements() {
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public void addDepartement(Departement departement) {
        this.departements.add(departement);
    }
}

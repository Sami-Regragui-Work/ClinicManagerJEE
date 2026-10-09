package org.jee.clinicmanager.repository.imp;

import jakarta.persistence.EntityManager;
import org.jee.clinicmanager.model.Availability;
import org.jee.clinicmanager.repository.AvailabilityRepository;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityRepositoryImp extends BaseRepositoryImp implements AvailabilityRepository {
    public AvailabilityRepositoryImp(EntityManager em) {
        super(em);
    }

    @Override
    public Availability findById(Long id) {
        return this.em.find(Availability.class, id);
    }

    @Override
    public List<Availability> findByDoctorId(Long doctorId) {
        return this.em.createQuery("SELECT a FROM Availability a WHERE a.doctor.id = :id", Availability.class)
                .setParameter("id", doctorId)
                .getResultList();
    }

    @Override
    public List<Availability> findByDoctorIdAndFutureDate(Long doctorId, LocalDate startingDate) {
        return this.em.createQuery("SELECT a FROM Availability a WHERE a.doctor.id = :id AND a.day >= :date", Availability.class)
                .setParameter("id", doctorId)
                .setParameter("date", startingDate)
                .getResultList();
    }

    @Override
    public Availability save(Availability availability) {
        if (availability.getId() == null) {
            this.em.persist(availability);
            return availability;
        }
        return this.em.merge(availability);
    }

    @Override
    public void delete(Availability availability) {
        if (this.em.contains(availability)) this.em.remove(availability);
        else this.em.remove(this.em.merge(availability));
    }
}

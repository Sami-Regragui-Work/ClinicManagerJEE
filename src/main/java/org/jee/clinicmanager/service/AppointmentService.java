package org.jee.clinicmanager.service;

import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.model.Availability;
import org.jee.clinicmanager.repository.AppointmentRepository;
import org.jee.clinicmanager.repository.AvailabilityRepository;
import org.jee.clinicmanager.util.AppointmentRules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, AvailabilityRepository availabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public boolean canReschedule(Appointment appointment) {
        if (!AppointmentRules.canCancel(appointment)) return false;

        Long doctorId = appointment.getDoctor().getId();
        List<Availability> futureAvailabilities = this.availabilityRepository.findByDoctorIdAndFutureDate(doctorId, LocalDate.now());

        for (Availability availability: futureAvailabilities) {
            if (this.hasBookableSlot(availability, doctorId)) return true;
        }

        return false;
    }

    private boolean hasBookableSlot(Availability availability, Long doctorId) {
        LocalDateTime now = LocalDateTime.now();

        LocalTime chosenStartingTime = availability.getStartingTime();
        LocalTime availableEndingTime = availability.getEndingTime();

        LocalDate day = availability.getDay();

        do {
            LocalDateTime slotStart = LocalDateTime.of(day, chosenStartingTime);

            // if the chosen start of the slot starts in less than CANCELLATION_LEAD_HOURS gap
            if (ChronoUnit.HOURS.between(now, slotStart) < AppointmentRules.CANCELLATION_LEAD_HOURS) {
                chosenStartingTime = chosenStartingTime.plusMinutes(AppointmentRules.slotWithBufferMinutes());
                continue;
            }

            // if the calculated start of the slot is within lunch break
            if (!chosenStartingTime.isBefore(AppointmentRules.LUNCH_START_BREAK_TIME) && chosenStartingTime.isBefore(AppointmentRules.LUNCH_END_BREAK_TIME)) {
                chosenStartingTime = AppointmentRules.LUNCH_END_BREAK_TIME;
                continue;
            }

            // if no overlap with an existing appointment happens then we found it
            if (!this.appointmentRepository.existsByDoctorAndTimeRange(doctorId, day, chosenStartingTime, chosenStartingTime.plusMinutes(AppointmentRules.SLOT_DURATION_MINUTES))) {
                return true;
            }

            chosenStartingTime = chosenStartingTime.plusMinutes(AppointmentRules.slotWithBufferMinutes());
        } while (!chosenStartingTime.plusMinutes(AppointmentRules.SLOT_DURATION_MINUTES).isAfter(availableEndingTime));

        return false;
    }
}

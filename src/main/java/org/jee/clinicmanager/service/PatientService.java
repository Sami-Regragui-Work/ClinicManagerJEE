package org.jee.clinicmanager.service;

import org.jee.clinicmanager.dto.request.BookAppointmentDTO;
import org.jee.clinicmanager.exception.appointment.LeadTimeViolationException;
import org.jee.clinicmanager.exception.appointment.UnavailableSlotException;
import org.jee.clinicmanager.exception.appointment.UnauthorizedActionException;
import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.model.Availability;
import org.jee.clinicmanager.model.Doctor;
import org.jee.clinicmanager.model.Patient;
import org.jee.clinicmanager.model.enums.AppointmentStatus;
import org.jee.clinicmanager.repository.AppointmentRepository;
import org.jee.clinicmanager.repository.AvailabilityRepository;
import org.jee.clinicmanager.repository.DoctorRepository;
import org.jee.clinicmanager.repository.PatientRepository;
import org.jee.clinicmanager.util.AppointmentRules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PatientService {
    private final PatientRepository patientRepository;
    private final AvailabilityRepository availabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository, AvailabilityRepository availabilityRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.availabilityRepository = availabilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment bookAppointment(Long patientId, BookAppointmentDTO bookAppointmentDTO) {
        Patient patient = this.patientRepository.findById(patientId);
        if (patient == null)
            throw new UnauthorizedActionException("No patient profile found for this user");

        Availability availability = this.availabilityRepository.findById(bookAppointmentDTO.getSlotId());
        if (availability == null)
            throw new UnavailableSlotException("Selected time slot does not exist");

        Doctor doctor = availability.getDoctor();

        LocalDate availableDay = availability.getDay();
        LocalTime availableStartingTime = availability.getStartingTime();

        LocalTime inputStartingTime = bookAppointmentDTO.getStartingTime();
        LocalDateTime inputDateTime = LocalDateTime.of(availableDay, inputStartingTime);

        if (inputDateTime.isBefore(LocalDateTime.now().plusHours(AppointmentRules.BOOKING_LEAD_HOURS)))
            throw new LeadTimeViolationException("Appointments must be booked at least 2 hours in advance");

        LocalTime availableEndingTime = availability.getEndingTime();
        LocalTime calculatedEndingTime = inputStartingTime.plusMinutes(AppointmentRules.SLOT_DURATION_MINUTES);

        if (inputStartingTime.isBefore(availableStartingTime) || calculatedEndingTime.isAfter(availableEndingTime))
            throw new UnavailableSlotException("Requested time is outside doctor's availability");

        if (this.appointmentRepository.existsByDoctorAndTimeRange(doctor.getId(), availableDay, inputStartingTime, calculatedEndingTime))
            throw new UnavailableSlotException("This time slot is already booked");


        return this.appointmentRepository.save(
            new Appointment(
                    patient,
                    doctor,
                    availableDay,
                    inputStartingTime,
                    bookAppointmentDTO.getType(),
                    AppointmentStatus.PLANNED,
                    bookAppointmentDTO.getReason()
            )
        );
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        return this.appointmentRepository.findByPatientId(patientId);
    }
}

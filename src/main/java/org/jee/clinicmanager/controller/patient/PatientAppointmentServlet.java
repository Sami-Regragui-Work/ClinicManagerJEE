package org.jee.clinicmanager.controller.patient;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.ConstraintViolation;
import org.jee.clinicmanager.controller.BaseServlet;
import org.jee.clinicmanager.dto.request.BookAppointmentDTO;
import org.jee.clinicmanager.dto.response.AppointmentDetailDTO;
import org.jee.clinicmanager.dto.response.AppointmentListItemDTO;
import org.jee.clinicmanager.dto.response.SpecialtySelectDTO;
import org.jee.clinicmanager.mapper.AppointmentMapper;
import org.jee.clinicmanager.model.Appointment;
import org.jee.clinicmanager.model.MedicalNote;
import org.jee.clinicmanager.model.enums.AppointmentType;
import org.jee.clinicmanager.repository.AppointmentRepository;
import org.jee.clinicmanager.repository.MedicalNoteRepository;
import org.jee.clinicmanager.repository.imp.*;
import org.jee.clinicmanager.service.AppointmentService;
import org.jee.clinicmanager.service.PatientService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/patient")
public class PatientAppointmentServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = BaseServlet.getUserIdFromSessionOrRedirectToLogin(req, resp);
        if (userId == null) return;

        try (EntityManager em = this.emf.createEntityManager()) {
            String path = req.getPathInfo();
            if (path == null) path = "/";

            final String pathPrefix = "/WEB-INF/patient";

            if (path.equals("/appointments")) {
                AppointmentRepository appointmentRepository = new AppointmentRepositoryImp(em);
                List<Appointment> appointments = appointmentRepository.findByPatientId(userId);

                List<AppointmentListItemDTO> appointmentListItemDTOs = appointments.stream().map(AppointmentMapper::toListItem).toList();

                req.setAttribute("appointments", appointmentListItemDTOs);
                req.getRequestDispatcher(pathPrefix + "/appointment.index.jsp").forward(req, resp);
            } else if (path.equals("/appointments/new")) {
                List<SpecialtySelectDTO> specialtySelectDTOs = (new SpecialtyRepositoryImp(em)).findAll()
                        .stream().map(specialty -> new SpecialtySelectDTO(specialty.getId(), specialty.getTitle())).toList();
                req.setAttribute("specialities", specialtySelectDTOs);
                req.getRequestDispatcher(pathPrefix + "/appointment.form.jsp").forward(req, resp);
            } else if (path.startsWith("/appointments/")) {
                try {
                    Long appointmentId = Long.valueOf(path.split("/")[2]);

                    AppointmentRepository appointmentRepository = new AppointmentRepositoryImp(em);
                    MedicalNoteRepository medicalNoteRepository = new MedicalNoteRepositoryImp(em);

                    Appointment appointment = appointmentRepository.findById(appointmentId);

                    if (appointment == null || !appointment.getPatient().getId().equals(userId)) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    MedicalNote medicalNote = medicalNoteRepository.findByAppointmentId(appointmentId);

                    AppointmentService appointmentService = new AppointmentService(new AppointmentRepositoryImp(em), new AvailabilityRepositoryImp(em));
                    boolean canReschedule = appointmentService.canReschedule(appointment);

                    req.setAttribute("appointment", AppointmentMapper.toDetail(appointment, medicalNote, canReschedule));
                    req.getRequestDispatcher(pathPrefix + "/appointment.show.jsp").forward(req, resp);
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = BaseServlet.getUserIdFromSessionOrRedirectToLogin(req, resp);
        if (userId == null) return;

        EntityManager em = this.emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            Long slotId = Long.parseLong(req.getParameter("slotId"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            LocalTime startingTime = LocalTime.parse(req.getParameter("startingTime"));
            AppointmentType type = AppointmentType.valueOf(req.getParameter("type"));
            String reason = req.getParameter("reason");

            BookAppointmentDTO bookAppointmentDTO = new BookAppointmentDTO(slotId, date, startingTime, type, reason);

            Set<ConstraintViolation<BookAppointmentDTO>> violations = this.validator.validate(bookAppointmentDTO);

            if (!violations.isEmpty()) {
                req.setAttribute("formError", violations.stream().map(ConstraintViolation::getMessage).findFirst().orElse("Invalid input"));
                req.getRequestDispatcher("/WEB-INF/patient/appointment.index.jsp").forward(req, resp);
                return;
            }

            transaction.begin();
            Appointment appointment = (new PatientService(new PatientRepositoryImp(em), new AvailabilityRepositoryImp(em), new AppointmentRepositoryImp(em), new DoctorRepositoryImp(em))).bookAppointment(userId, bookAppointmentDTO);
            transaction.commit();
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
        } catch (NumberFormatException | DateTimeParseException e) {
            if (transaction.isActive()) transaction.rollback();
            req.setAttribute("formError", "Invalid date/time format");
            req.getRequestDispatcher("/WEB-INF/patient/appointment.index.jsp").forward(req, resp);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Booking failed", e);
            req.setAttribute("formError", "Unable to book appointment. Please try again.");
            req.getRequestDispatcher("/WEB-INF/patient/appointment.form.jsp").forward(req, resp);
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}

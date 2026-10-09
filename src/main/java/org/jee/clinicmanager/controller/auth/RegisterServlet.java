package org.jee.clinicmanager.controller.auth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import org.jee.clinicmanager.controller.BaseServlet;
import org.jee.clinicmanager.dto.request.auth.UserRegistrationDTO;
import org.jee.clinicmanager.exception.user.DuplicateEmailException;
import org.jee.clinicmanager.repository.imp.UserRepositoryImp;
import org.jee.clinicmanager.service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/auth/register")
public class RegisterServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");

            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(firstName, lastName, email, phone, password);

            Set<ConstraintViolation<UserRegistrationDTO>> violations = this.validator.validate(userRegistrationDTO);

            if (!violations.isEmpty()) {
                List<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage).toList();

                req.setAttribute("errors", errorMessages);
                req.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(req, resp);
                return;
            }

            transaction.begin();
            (new AuthService(new UserRepositoryImp(em))).registerPatient(userRegistrationDTO);
            transaction.commit();

            resp.sendRedirect(req.getContextPath() + "/auth/login?registered=true");
        } catch (DuplicateEmailException dee) {
            if (transaction.isActive()) transaction.rollback();
            req.setAttribute("formError", dee.getMessage());
            req.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(req, resp);
        } catch (Exception e) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, "Unexpected system error during registration", e);
            req.setAttribute("formError", "Unexpected system error occurred");
            req.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(req, resp);
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}

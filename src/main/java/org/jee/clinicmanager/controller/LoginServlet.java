package org.jee.clinicmanager.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import org.jee.clinicmanager.dto.UserLoginDTO;

import jakarta.validation.Validator;
import org.jee.clinicmanager.exception.UserException;
import org.jee.clinicmanager.model.User;
import org.jee.clinicmanager.repository.imp.UserRepositoryImp;
import org.jee.clinicmanager.service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private EntityManagerFactory emf;
    private Validator validator;


    @Override
    public void init() {
        this.emf = (EntityManagerFactory) getServletContext().getAttribute("entityManagerFactory");
        this.validator = (Validator) getServletContext().getAttribute("validator");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/patient");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (EntityManager em = this.emf.createEntityManager()) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
            Set<ConstraintViolation<UserLoginDTO>> violations = this.validator.validate(userLoginDTO);

            if (!violations.isEmpty()) {
                List<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage).toList();

                req.setAttribute("errors", errorMessages);
                req.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(req, resp);
                return;
            }

            User user = (new AuthService(new UserRepositoryImp(em))).authenticate(userLoginDTO);

            req.getSession().setAttribute("userId", user.getId());
            resp.sendRedirect(req.getContextPath() + "/patient");
        } catch (UserException e) {
            req.setAttribute("formError", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(req, resp);
        }

    }
}

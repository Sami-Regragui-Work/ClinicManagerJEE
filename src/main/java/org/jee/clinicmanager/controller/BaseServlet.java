package org.jee.clinicmanager.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Validator;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected EntityManagerFactory emf;
    protected Validator validator;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext ctx = getServletContext();
        this.emf = (EntityManagerFactory) ctx.getAttribute("entityManagerFactory");
        this.validator = (Validator) ctx.getAttribute("validator");

        if (this.emf == null || this.validator == null) {
            throw new ServletException("AppStartupListener has not initialized required context attributes");
        }
    }

    protected static @Nullable Long getUserIdFromSessionOrRedirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;

        if (userId == null) {
            resp.sendRedirect( req.getContextPath() + "/auth/login");
            return null;
        }
        return userId;
    }
}

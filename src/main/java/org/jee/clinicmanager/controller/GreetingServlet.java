package org.jee.clinicmanager.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String name = req.getParameter("name");
        if (name == null) name = "Guest";

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter output = resp.getWriter();
        output.println("""
                <html>
                <body>
                    <h1>Hello, %s!</h1>
                </body>
                </html>
                """.formatted(name));
    }
}

package org.jee.clinicmanager.listener;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.jee.clinicmanager.util.DatabaseInitializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@WebListener
public class AppStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, Object> dbProperties = new HashMap<>();
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) properties.load(input);
        } catch (IOException e) {
            System.out.println("if you're running locally then properties must be correctly configured");
            e.printStackTrace();
        }

        String dbName = System.getenv("DB_NAME");
        if (dbName == null) dbName = properties.getProperty("db.name");

        String dbUrlStart = System.getenv("DB_URL_START");
        if (dbUrlStart == null) dbUrlStart = "%s://%s:%s/".formatted(properties.getProperty("db.urlPrefix"), properties.getProperty("db.host"), properties.getProperty("db.port"));

        String dbUrl = dbUrlStart + dbName;

        String dbUser = System.getenv("DB_USER");
        if (dbUser == null) dbUser = properties.getProperty("db.user");

        String dbPassword = "";

        String passwordFile = System.getenv("DB_PASSWORD_FILE");

        if (passwordFile != null) {
            try {
                dbPassword = Files.readString(Path.of(passwordFile)).trim();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read DB password file", e);
            }
        } else {
            dbPassword = properties.getProperty("db.password");
        }

        DatabaseInitializer.ensureDatabaseExist(dbUrlStart, dbUser, dbPassword, dbName);

        dbProperties.put("jakarta.persistence.jdbc.url", dbUrl);
        dbProperties.put("jakarta.persistence.jdbc.user", dbUser);
        dbProperties.put("jakarta.persistence.jdbc.password", dbPassword);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinic-manager", dbProperties);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        sce.getServletContext().setAttribute("entityManagerFactory", emf);
        sce.getServletContext().setAttribute("validatorFactory", validatorFactory);
        sce.getServletContext().setAttribute("validator", validator);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("entityManagerFactory");
        ValidatorFactory validatorFactory = (ValidatorFactory) sce.getServletContext().getAttribute("validatorFactory");

        if (emf != null && emf.isOpen()) emf.close();
        if (validatorFactory != null) validatorFactory.close();
    }
}

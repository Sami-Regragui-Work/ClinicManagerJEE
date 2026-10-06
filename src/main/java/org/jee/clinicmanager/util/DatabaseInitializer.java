package org.jee.clinicmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void ensureDatabaseExist(String dbUrlStart, String dbUser, String dbPassword, String dbName) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
        try (Connection connection = DriverManager.getConnection(dbUrlStart + dbName, dbUser, dbPassword)) {
            System.out.println("Database '" + dbName + "' exists. Skipping creation.");
            return;
        } catch (SQLException e) {
            // 3D000 is the SQL State for database does not exist
            if (!"3D000".equals(e.getSQLState())) throw new RuntimeException("Failed to connect to database '" + dbName + "': " + e.getMessage(), e);
        }

        try (Connection connection = DriverManager.getConnection(dbUrlStart + "postgres", dbUser, dbPassword); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("Database '" + dbName + "' created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database '" + dbName + "': " + e.getMessage(), e);
        }
    }
}

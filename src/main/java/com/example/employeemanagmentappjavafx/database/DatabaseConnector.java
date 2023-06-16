package com.example.employeemanagmentappjavafx.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;

public class DatabaseConnector {

    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);
    static ResourceBundle bundle = ResourceBundle.getBundle("application");
    static String dbUrl = bundle.getString("db.url");
    static String dbUsername = bundle.getString("db.username");
    static String dbPassword = bundle.getString("db.password");

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("Error connecting to mySql database");
            return null;
        }
    }

    public static ResultSet executeQuery(String query) {
        Connection connection = getConnection();
        Statement st;
        ResultSet rs;
        try {
            assert connection != null;
            st = connection.createStatement();
            rs = st.executeQuery(query);
            return rs;

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("Error executing: " + query + " query");
        }
        return null;
    }

    public static void executeUpdate(String query) {
        Connection connection = getConnection();
        try {
            assert connection != null;
            try (Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            logger.info("Error executing update query: " + query);
        }
    }

}

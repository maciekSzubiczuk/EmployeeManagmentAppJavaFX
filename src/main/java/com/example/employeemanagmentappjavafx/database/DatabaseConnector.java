package com.example.employeemanagmentappjavafx.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DatabaseConnector {

    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);

    public static Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_employee_tracker","employeeJavaFX"
                    ,"employeeJavaFX");
            return connection;
        } catch (Exception exception){
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
            logger.info("Error executing: "+query+" query");
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

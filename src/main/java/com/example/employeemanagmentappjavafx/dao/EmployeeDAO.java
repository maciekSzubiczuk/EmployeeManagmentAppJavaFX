package com.example.employeemanagmentappjavafx.dao;

import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.employeemanagmentappjavafx.database.DatabaseConnector.executeUpdate;
import static com.example.employeemanagmentappjavafx.database.DatabaseConnector.getConnection;

public class EmployeeDAO {

    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    private DatabaseConnector databaseConnector;

    public EmployeeDAO(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public ObservableList<Employee> getEmployeeList() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM employee";
        Statement st;
        ResultSet rs;
        try {
            assert connection != null;
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Employee employee;
            while (rs.next()) {
                employee = new Employee(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getFloat("salary"), rs.getString("vacation_end"));
                employeeList.add(employee);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("Error executing getEmployeeList query");
            return null;
        }
        return employeeList;
    }

    public static void insertEmployee(Employee employee){
        String query = "INSERT INTO employee (`first_name`, `last_name`, `salary`, `vacation_end`) " +
                "VALUES ('"+employee.getFirstName()+
                "','"+employee.getLastName()+"','"+employee.getSalary()+"','"+employee.getVacationEnd()+"');";
        logger.info(query);
        executeUpdate(query);
    }

    public static void updateEmployee(Employee newEmployeeData,int idToUpdate){
        String query = "UPDATE employee SET first_name = '"+newEmployeeData.getFirstName()+"', last_name= '"+newEmployeeData.getLastName()
                +"', salary = '"+newEmployeeData.getSalary()+"' , vacation_end = '"+newEmployeeData.getVacationEnd()
                +"' WHERE id = "+idToUpdate+";";
        executeUpdate(query);
    }
}


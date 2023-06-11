package com.example.employeemanagmentappjavafx.dao;

import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Objects;

import static com.example.employeemanagmentappjavafx.database.DatabaseConnector.executeUpdate;
import static com.example.employeemanagmentappjavafx.database.DatabaseConnector.getConnection;

public class EmployeeDAO {

    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    private DatabaseConnector databaseConnector;

    private static FileInputStream fileInputStream;

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

    public static Employee getEmployee(int id) {
        Connection connection = getConnection();
        String query = "SELECT * FROM employee WHERE id = ?";
        Employee employee = new Employee();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setSalary(rs.getFloat("salary"));
                employee.setVacationEnd(rs.getString("vacation_end"));
                employee.setPhoto(rs.getBinaryStream("photo"));
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static void insertEmployee(Employee employee) {
        String query = "INSERT INTO employee (`first_name`, `last_name`, `salary`, `vacation_end`,`photo`)  " +
                " VALUES (?, ?, ?, ?, ?)";
        executeEmployeeQuery(employee,query);
    }

    public static void updateEmployee(Employee newEmployeeData) {
        String query = "UPDATE employee SET first_name = ?, last_name = ?, salary = ?," +
                " vacation_end = ?, photo = ?  " +
                " WHERE id = "+newEmployeeData.getId();
        logger.info(query);
        executeEmployeeQuery(newEmployeeData,query);
    }

    public static void executeEmployeeQuery(Employee employeeData,String query) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Objects.requireNonNull(getConnection()).prepareStatement(query);
            preparedStatement.setString(1,employeeData.getFirstName());
            preparedStatement.setString(2,employeeData.getLastName());
            preparedStatement.setFloat(3,employeeData.getSalary());
            preparedStatement.setString(4,employeeData.getVacationEnd());
            preparedStatement.setBinaryStream(5,null);
            if(employeeData.getPhoto()!=null){
                preparedStatement.setBinaryStream(5,employeeData.getPhoto());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void updateEmployee(Employee newEmployeeData,int idToUpdate){
        String query = "UPDATE employee SET first_name = '"+newEmployeeData.getFirstName()+"', last_name= '"+newEmployeeData.getLastName()
                +"', salary = '"+newEmployeeData.getSalary()+"' , vacation_end = '"+newEmployeeData.getVacationEnd()
                +"' WHERE id = "+idToUpdate+";";
        executeUpdate(query);
    }

     */
}


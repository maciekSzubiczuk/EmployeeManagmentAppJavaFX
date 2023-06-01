package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.DataManager;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.dao.EmployeeDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;


public class ListEmployeesController implements Initializable {

    @FXML
    private TableView<Employee> tvEmployees;

    @FXML
    private TableColumn<Employee, Integer> colId;

    @FXML
    private TableColumn<Employee, String> colFirstName;

    @FXML
    private TableColumn<Employee, String> colLastName;

    @FXML
    private TableColumn<Employee, Float> colSalary;

    @FXML
    private TableColumn<Employee, LocalDate> colVacationEnd;

    private static final Logger logger = LogManager.getLogger(ListEmployeesController.class);

    private DatabaseConnector databaseConnector = new DatabaseConnector();

    private EmployeeDAO employeeDAO = new EmployeeDAO(databaseConnector);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvEmployees.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Employee clickedEmployee = row.getItem();
                    logger.info(clickedEmployee.getFirstName());
                    DataManager.getInstance().setSelectedEmployee(clickedEmployee);
                    ViewSwitcher.switchTo(View.EDIT_EMPLOYEE);
                }
            });
            return row ;
        });
        showEmployees();
    }

    public void showEmployees() {
        ObservableList<Employee> employeeList = employeeDAO.getEmployeeList();

        colId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colSalary.setCellValueFactory(new PropertyValueFactory<Employee, Float>("salary"));
        colVacationEnd.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("vacationEnd"));

        tvEmployees.setItems(employeeList);
    }

}

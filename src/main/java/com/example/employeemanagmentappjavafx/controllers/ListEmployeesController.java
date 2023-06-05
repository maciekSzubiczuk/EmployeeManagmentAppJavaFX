package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.DataManager;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.dao.EmployeeDAO;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    @FXML
    private ProgressIndicator piListEmployeesLoading;

    private static final Logger logger = LogManager.getLogger(ListEmployeesController.class);

    private DatabaseConnector databaseConnector = new DatabaseConnector();

    private EmployeeDAO employeeDAO = new EmployeeDAO(databaseConnector);

    private Executor exec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvEmployees.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Employee clickedEmployee = row.getItem();
                    DataManager.getInstance().setSelectedEmployee(clickedEmployee);
                    ViewSwitcher.switchTo(View.EDIT_EMPLOYEE);
                }
            });
            return row ;
        });
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });
        showEmployees();
    }

    public void showEmployees() {

        piListEmployeesLoading.setProgress(-1);
        ObservableList<Employee> employeeList = employeeDAO.getEmployeeList();
        Task<ObservableList<Employee>> employeeListTask = new Task<ObservableList<Employee>>() {
            @Override
            public ObservableList<Employee> call() throws Exception {
                logger.info("running thread: "+Thread.currentThread().getName());
                Thread.sleep(5000);
                return employeeDAO.getEmployeeList();
            }
        };

        employeeListTask.setOnFailed(e -> {
            employeeListTask.getException().printStackTrace();
            // inform user of error...
        });

        employeeListTask.setOnSucceeded(e -> {
            // Task.getValue() gives the value returned from call()...
            colId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
            colLastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
            colSalary.setCellValueFactory(new PropertyValueFactory<Employee, Float>("salary"));
            colVacationEnd.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("vacationEnd"));

            tvEmployees.setItems(employeeList);

            // Stopping the spinning animation
            piListEmployeesLoading.setProgress(1);
        });
        exec.execute(employeeListTask);


    }

}

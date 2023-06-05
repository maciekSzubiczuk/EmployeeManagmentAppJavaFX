package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.insertEmployee;

public class CreateEmployeeController {

    @FXML
    private Button btnAddEmployee;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfSalary;

    @FXML
    private TextField tfVacation;

    private static final Logger logger = LogManager.getLogger(CreateEmployeeController.class);

    @FXML
    protected void onCreateBtnClick() {
        insertEmployee(new Employee(tfFirstName.getText(),
        tfLastName.getText(),Float.parseFloat(tfSalary.getText()),tfVacation.getText()));
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onShowEmployeeListBtnClick() {
        logger.info("running thread: "+Thread.currentThread().getName());
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }


}
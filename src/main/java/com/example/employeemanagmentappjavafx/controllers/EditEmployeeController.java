package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.DataManager;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.models.Employee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.updateEmployee;

public class EditEmployeeController implements Initializable {

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfSalary;

    @FXML
    private TextField tfVacation;

    @FXML
    private Button btnEditEmployee;

    @FXML
    private Button cancelBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEmployee();
    }

    public void showEmployee() {
        Employee employee = DataManager.getInstance().getSelectedEmployee();
        tfFirstName.setText(employee.getFirstName());
        tfLastName.setText(employee.getLastName());
        tfSalary.setText(String.valueOf(employee.getSalary()));
        tfVacation.setText(String.valueOf(employee.getVacationEnd()));
    }

    @FXML
    protected void onBtnEditEmployeeClick(){
        Employee selectedEmployee = DataManager.getInstance().getSelectedEmployee();
        Employee newEmployeeData = new Employee(tfFirstName.getText(),tfLastName.getText(),
                Float.parseFloat(tfSalary.getText()),tfVacation.getText());
        updateEmployee(newEmployeeData,selectedEmployee.getId());
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onCancelBtnClick(){
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

}

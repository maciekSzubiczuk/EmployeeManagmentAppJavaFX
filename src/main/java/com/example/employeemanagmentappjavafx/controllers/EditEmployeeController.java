package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.utils.DataManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;

import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.*;
import static com.example.employeemanagmentappjavafx.utils.FileUtil.inputStreamToImage;
import static com.example.employeemanagmentappjavafx.utils.ThreadHelper.createExecutor;

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
    ImageView ivEmployeePhoto;
    @FXML
    private Button btnEditEmployee;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button btnChangePhoto;
    @FXML
    private Button btnDeleteEmployee;


    private File file = null;
    private final Desktop desktop = Desktop.getDesktop();
    private Executor exec;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exec = createExecutor();
        showEmployee();
    }

    public void showEmployee() {
        Employee employee = DataManager.getInstance().getSelectedEmployee();
        tfFirstName.setText(employee.getFirstName());
        tfLastName.setText(employee.getLastName());
        tfSalary.setText(String.valueOf(employee.getSalary()));
        tfVacation.setText(String.valueOf(employee.getVacationEnd()));

        if (employee.getPhoto() != null) {
            try {
                ivEmployeePhoto.setImage(inputStreamToImage(employee.getPhoto()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onBtnEditEmployee() {
        Employee selectedEmployee = DataManager.getInstance().getSelectedEmployee();
        Employee newEmployeeData = new Employee(selectedEmployee.getId(), tfFirstName.getText(), tfLastName.getText(),
                Float.parseFloat(tfSalary.getText()), tfVacation.getText());
        if (file != null) {
            try {
                newEmployeeData.setPhoto(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Task<Void> updateTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                updateEmployee(newEmployeeData);
                return null;
            }
        };

        updateTask.setOnFailed(e -> {
            updateTask.getException().printStackTrace();
        });

        updateTask.setOnSucceeded(e -> {
            ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
        });

        exec.execute(updateTask);
    }

    @FXML
    protected void onBtnChangePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = fileChooser.showOpenDialog(btnChangePhoto.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            ivEmployeePhoto.setImage(image);
        }
    }

    @FXML
    protected void onCancelBtnClick() {
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onBtnDeleteEmployee() {
        Task<Void> deleteTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                deleteEmployee(DataManager.getInstance().getSelectedEmployee().getId());
                return null;
            }
        };

        deleteTask.setOnFailed(e -> {
            deleteTask.getException().printStackTrace();
        });

        deleteTask.setOnSucceeded(e -> {
            ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
        });

        exec.execute(deleteTask);
    }
}

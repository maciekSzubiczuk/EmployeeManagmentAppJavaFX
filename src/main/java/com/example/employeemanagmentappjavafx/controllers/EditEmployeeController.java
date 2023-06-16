package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.utils.DataManager;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import com.example.employeemanagmentappjavafx.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.deleteEmployee;
import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.updateEmployee;
import static com.example.employeemanagmentappjavafx.utils.FileUtil.inputStreamToImage;

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

    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private FileChooser fileChooser;
    private File file = null;
    private Desktop desktop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fileInputStream;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployee() throws IOException {
        Employee employee = DataManager.getInstance().getSelectedEmployee();
        tfFirstName.setText(employee.getFirstName());
        tfLastName.setText(employee.getLastName());
        tfSalary.setText(String.valueOf(employee.getSalary()));
        tfVacation.setText(String.valueOf(employee.getVacationEnd()));

        if (employee.getPhoto() != null) {
            ivEmployeePhoto.setImage(inputStreamToImage(employee.getPhoto()));
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
        updateEmployee(newEmployeeData);
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onBtnChangePhoto() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = fileChooser.showOpenDialog(btnChangePhoto.getScene().getWindow());
        if (file != null) {
            try {
                desktop.open(file);
                image = new Image(file.toURI().toString());
                ivEmployeePhoto.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onCancelBtnClick() {
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onBtnDeleteEmployee() {
        deleteEmployee(DataManager.getInstance().getSelectedEmployee().getId());
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }
}

package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.models.Employee;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


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

    @FXML
    private Button btnAddPhoto;

    @FXML
    private ImageView ivEmployeePhoto;

    private static final Logger logger = LogManager.getLogger(CreateEmployeeController.class);
    private FileChooser fileChooser;
    private File file;
    private Desktop desktop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fileInputStream;

    @FXML
    protected void onBtnAddPhoto(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg")
        );
        file = fileChooser.showOpenDialog(btnAddPhoto.getScene().getWindow());
        if(file!=null){
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
    protected void onCreateBtnClick() {
        Employee newEmployee = new Employee(tfFirstName.getText(),
                tfLastName.getText(),Float.parseFloat(tfSalary.getText()),tfVacation.getText());
        if(file!=null){
            try {
                newEmployee.setPhoto(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        insertEmployee(newEmployee);
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    @FXML
    protected void onShowEmployeeListBtnClick() {
        logger.info("running thread: "+Thread.currentThread().getName());
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }


}
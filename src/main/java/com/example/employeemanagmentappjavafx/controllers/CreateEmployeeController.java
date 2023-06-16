package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.models.Employee;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.function.UnaryOperator;

import static com.example.employeemanagmentappjavafx.dao.EmployeeDAO.insertEmployee;
import static com.example.employeemanagmentappjavafx.utils.ThreadHelper.createExecutor;

public class CreateEmployeeController implements Initializable {

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

    private Executor exec;
    private static final Logger logger = LogManager.getLogger(CreateEmployeeController.class);
    private File file;
    private final Desktop desktop = Desktop.getDesktop();
    private static ResourceBundle bundle = ResourceBundle.getBundle("application");
    private static final int MAX_LENGTH = 10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupVacationFieldValidator();
        setupSalaryFieldValidator();
        exec = createExecutor();
    }

    @FXML
    protected void onBtnAddPhoto() {
        FileChooser fileChooser = new FileChooser();
        Image image;
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = fileChooser.showOpenDialog(btnAddPhoto.getScene().getWindow());
        if (file != null) {
            image = new Image(file.toURI().toString());
            ivEmployeePhoto.setImage(image);
        }

    }

    @FXML
    protected void onCreateBtnClick() {
        Employee newEmployee = new Employee(tfFirstName.getText(),
                tfLastName.getText(), Float.parseFloat(tfSalary.getText()), tfVacation.getText());
        if (file != null) {
            try {
                newEmployee.setPhoto(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Task<Void> insertTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                insertEmployee(newEmployee);
                return null;
            }
        };

        insertTask.setOnFailed(e -> {
            insertTask.getException().printStackTrace();
        });

        insertTask.setOnSucceeded(e -> {
            ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
        });

        exec.execute(insertTask);
    }

    @FXML
    protected void onShowEmployeeListBtnClick() {
        logger.info("running thread: " + Thread.currentThread().getName());
        ViewSwitcher.switchTo(View.LIST_EMPLOYEES);
    }

    private void setupVacationFieldValidator() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9-]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        tfVacation.setTextFormatter(textFormatter);

        tfVacation.setOnKeyTyped(event -> {
            String text = tfVacation.getText();
            // Check if maximum length has been reached
            if (text.length() >= 10) {
                tfVacation.setText(text.substring(0, MAX_LENGTH));
                tfVacation.positionCaret(MAX_LENGTH);
                event.consume();
                return;
            }

            // adding dashes after year and month
            if (text.length() == 4 || text.length() == 7) {
                tfVacation.setText(text + "-");
                tfVacation.positionCaret(tfVacation.getLength());
            }
        });

        tfVacation.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                String text = tfVacation.getText();
                int caretPosition = tfVacation.getCaretPosition();
                if (caretPosition > 0 && caretPosition < text.length() && text.charAt(caretPosition) == '-') {
                    tfVacation.setText(text.substring(0, caretPosition) + text.substring(caretPosition + 1));
                    tfVacation.positionCaret(caretPosition);
                    event.consume();
                }
            }
        });
    }

    private void setupSalaryFieldValidator() {
        UnaryOperator<TextFormatter.Change> filterSalary = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9.]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatterSalary = new TextFormatter<>(filterSalary);
        tfSalary.setTextFormatter(textFormatterSalary);
    }

}
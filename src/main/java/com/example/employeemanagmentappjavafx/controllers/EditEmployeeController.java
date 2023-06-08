package com.example.employeemanagmentappjavafx.controllers;

import com.example.employeemanagmentappjavafx.DataManager;
import com.example.employeemanagmentappjavafx.View;
import com.example.employeemanagmentappjavafx.ViewSwitcher;
import com.example.employeemanagmentappjavafx.database.DatabaseConnector;
import com.example.employeemanagmentappjavafx.models.Employee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    ImageView ivEmployeePhoto;

    @FXML
    private Button btnEditEmployee;

    @FXML
    private Button cancelBtn;

    private DatabaseConnector databaseConnector = new DatabaseConnector();


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

        // displaying the Image
        InputStream is = employee.getPhoto();
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("photo.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] content = new byte[1024];
        int size = 0;
        while ((size = is.read(content)) != -1){
            assert os != null;
            os.write(content,0,size);
        }
        assert os != null;
        os.close();
        is.close();
        Image image = new Image("file:photo.jpg");
        ivEmployeePhoto.setImage(image);
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

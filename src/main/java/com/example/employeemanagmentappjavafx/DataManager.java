package com.example.employeemanagmentappjavafx;

import com.example.employeemanagmentappjavafx.models.Employee;

public class DataManager {
    private static DataManager instance;
    private Employee selectedEmployee;
    private int selectedEmployeeId;

    private DataManager() {
        // private constructor to enforce singleton pattern
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public int getSelectedEmployeeId() {
        return selectedEmployeeId;
    }

    public void setSelectedEmployeeId(int selectedEmployeeId) {
        this.selectedEmployeeId = selectedEmployeeId;
    }
}

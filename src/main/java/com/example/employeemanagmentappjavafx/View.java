package com.example.employeemanagmentappjavafx;

public enum View {

    CREATE_EMPLOYEE("create-employee.fxml"),
    EDIT_EMPLOYEE("edit-employee.fxml"),
    LIST_EMPLOYEES("list-employees.fxml");


    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}

package com.example.employeemanagmentappjavafx.models;

import java.time.LocalDate;
import java.util.Date;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private float salary;
    private String vacationEnd;

    public Employee(int id, String firstName, String lastName, float salary, String vacationEnd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.vacationEnd = vacationEnd;
    }
    public Employee(String firstName, String lastName, float salary, String vacationEnd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.vacationEnd = vacationEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(String vacationEnd) {
        this.vacationEnd = vacationEnd;
    }
}

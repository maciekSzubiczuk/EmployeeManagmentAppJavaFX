package com.example.employeemanagmentappjavafx.models;

import java.io.File;
import java.io.InputStream;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private float salary;
    private String vacationEnd;
    private InputStream photo;

    public Employee(int id, String firstName, String lastName, float salary, String vacationEnd, InputStream photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.vacationEnd = vacationEnd;
        this.photo = photo;
    }

    public Employee(int id, String firstName, String lastName, float salary, String vacationEnd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.vacationEnd = vacationEnd;
        this.photo = null;
    }

    public Employee(String firstName, String lastName, float salary, String vacationEnd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.vacationEnd = vacationEnd;
        this.photo = null;
    }

    public Employee() {

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

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }
}

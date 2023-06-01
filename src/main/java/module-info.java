module com.example.employeemanagmentappjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens com.example.employeemanagmentappjavafx to javafx.fxml;
    exports com.example.employeemanagmentappjavafx;
    exports com.example.employeemanagmentappjavafx.controllers;
    opens com.example.employeemanagmentappjavafx.controllers to javafx.fxml;
    exports com.example.employeemanagmentappjavafx.database;
    opens com.example.employeemanagmentappjavafx.database to javafx.fxml;
    exports com.example.employeemanagmentappjavafx.dao;
    opens com.example.employeemanagmentappjavafx.dao to javafx.fxml;
    exports com.example.employeemanagmentappjavafx.models;
    opens com.example.employeemanagmentappjavafx.models to javafx.fxml;
}
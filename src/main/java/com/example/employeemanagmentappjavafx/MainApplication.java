package com.example.employeemanagmentappjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    static ResourceBundle bundle = ResourceBundle.getBundle("application");
    static String title = bundle.getString("title");
    static String logo = bundle.getString("logo");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new Pane();
        var scene = new Scene(root, 700, 400);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.CREATE_EMPLOYEE);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream(logo))));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }
}
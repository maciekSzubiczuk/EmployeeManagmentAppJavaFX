package com.example.employeemanagmentappjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new Pane();
        var scene = new Scene(root, 700, 400);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.CREATE_EMPLOYEE);
        stage.setScene(scene);
        stage.show();

    }
}
package com.alexander.geoip;

import com.alexander.geoip.view.MainView;

import javafx.application.Application;

import javafx.stage.Stage;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new MainView(primaryStage, "GeoIP", 750, 450);
    }

}
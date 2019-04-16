package com.alexander.geoip.view;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import java.util.Objects;

public class MainView {

    private static final int minWidth = 450;
    private static final int minHeight = 450;

    public MainView(Stage parentStage, String name, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("Main.fxml")));

        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            parentStage.setTitle(name);
            parentStage.setScene(new Scene(root, width, height));
            parentStage.setMinWidth(minWidth);
            parentStage.setMinHeight(minHeight);
            parentStage.show();
        }
    }

}

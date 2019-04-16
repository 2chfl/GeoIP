package com.alexander.geoip.view;

import com.alexander.geoip.controller.InformationController;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;
import javafx.stage.Modality;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import java.util.Objects;

public class InformationView {

    private static Stage stage;
    private static InformationController informationController;

    public InformationView(String name, int width, int height) {
        initialize(name, width, height);
    }

    public void show(Stage parentStage) {
        if (stage.getOwner() == null) {
            stage.initOwner(parentStage);
        }

        stage.showAndWait();
    }

    public InformationController getController() {
        return informationController;
    }

    public Stage getStage() {
        return stage;
    }

    private void initialize(String name, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("Information.fxml")));

        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            informationController = fxmlLoader.getController();

            stage = new Stage();
            stage.setTitle(name);
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
        }
    }

}
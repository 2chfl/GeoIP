package com.alexander.geoip.view;

import com.alexander.geoip.controller.IPAddressController;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;
import javafx.stage.Modality;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import java.util.Objects;

public class IPAddressView {

    private static Stage stage;
    private static IPAddressController ipAddressController;

    public IPAddressView(String name, int width, int height) {
        initialize(name, width, height);
    }

    public void show(Stage parentStage) {
        if (stage.getOwner() == null) {
            stage.initOwner(parentStage);
        }

        stage.showAndWait();
    }

    public IPAddressController getController() {
        return ipAddressController;
    }

    public Stage getStage() {
        return stage;
    }

    private void initialize(String name, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("IPAddress.fxml")));

        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            ipAddressController = fxmlLoader.getController();

            stage = new Stage();
            stage.setTitle(name);
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
        }
    }

}
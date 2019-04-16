package com.alexander.geoip.model.Exception;

import javafx.application.Platform;

import javafx.scene.control.Alert;

public class GeoIPException {

    public GeoIPException(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });

        Thread.currentThread().stop();
    }

}

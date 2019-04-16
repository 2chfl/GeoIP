package com.alexander.geoip.controller;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;

public class IPAddressController {

    @FXML
    private Pane paneMain;

    @FXML
    private TextArea textAreaIPAddress;

    @FXML
    private CheckBox checkBoxOnlyRussia;

    @FXML
    private CheckBox checkBoxOnlyHMAO;

    @FXML
    private CheckBox checkBoxOnlyCity;

    public String getIPAddress() { return textAreaIPAddress.getText().trim(); }

    public boolean getIsRussia() {
        return checkBoxOnlyRussia.isSelected();
    }

    public boolean getIsHMAO() {
        return checkBoxOnlyHMAO.isSelected();
    }

    public boolean getIsCity() {
        return checkBoxOnlyCity.isSelected();
    }

    private Stage getStage() {
        return (Stage) paneMain.getScene().getWindow();
    }

}

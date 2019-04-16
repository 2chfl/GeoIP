package com.alexander.geoip.controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class InformationController {

    @FXML
    private TextField textFieldIPAddress;

    @FXML
    private TextField textFieldCountry;

    @FXML
    private TextField textFieldRegion;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldNetwork;

    @FXML
    private TextField textFieldOwner;

    @FXML
    private TextField textFieldOther;

    public void setTextFieldIPAddress(String textFieldIPAddress) {
        this.textFieldIPAddress.setText(textFieldIPAddress);;
    }

    public void setTextFieldCountry(String textFieldCountry) {
        this.textFieldCountry.setText(textFieldCountry);
}

    public void setTextFieldRegion(String textFieldRegion) {
        this.textFieldRegion.setText(textFieldRegion);
    }

    public void setTextFieldCity(String textFieldCity) {
        this.textFieldCity.setText(textFieldCity);
    }

    public void setTextFieldNetwork(String textFieldNetwork) {
        this.textFieldNetwork.setText(textFieldNetwork);
    }

    public void setTextFieldOwner(String textFieldOwner) {
        this.textFieldOwner.setText(textFieldOwner);
    }

    public void setTextFieldOther(String textFieldOther) {
        this.textFieldOther.setText(textFieldOther);
    }

}

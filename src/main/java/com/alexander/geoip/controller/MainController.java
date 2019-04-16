package com.alexander.geoip.controller;

import com.alexander.geoip.model.DataStream;
import com.alexander.geoip.model.GeoIP;
import com.alexander.geoip.model.IPInfo;

import com.alexander.geoip.view.IPAddressView;
import com.alexander.geoip.view.InformationView;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.net.URL;

import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private IPAddressView ipAddressView;
    private IPAddressController ipAddressController;

    private InformationView informationView;
    private InformationController informationController;

    private ObservableList<IPInfo> ipInfoResult = FXCollections.observableArrayList();

    private FileChooser fileChooser = new FileChooser();

    private ContextMenu contextMenu;

    @FXML
    private AnchorPane anchorPaneMain;

    @FXML
    private Button btnIPAddress;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<IPInfo> tableViewIPInfo;

    @FXML
    private TableColumn<IPInfo, Integer> columnId;

    @FXML
    private TableColumn<IPInfo, String> columnAddress;

    @FXML
    private TableColumn<IPInfo, String> columnCountry;

    @FXML
    private TableColumn<IPInfo, String> columnRegion;

    @FXML
    private TableColumn<IPInfo, String> columnCity;

    @FXML
    private TableColumn<IPInfo, String> columnNetwork;

    @FXML
    private TableColumn<IPInfo, String> columnOwner;

    @FXML
    private TextArea textAreaWhois;

    public MainController() {
        ipAddressView = new IPAddressView("Настройки", 500, 560);
        informationView = new InformationView("Информация", 500, 370);

        ipAddressController = ipAddressView.getController();
        informationController = informationView.getController();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовой", "*.txt")
        );

        fileChooser.setTitle("Сохранить");

        contextMenu = new ContextMenu();

        MenuItem item = new MenuItem("В окне");

        item.setOnAction(event -> {
            IPInfo ipInfo = tableViewIPInfo.getSelectionModel().getSelectedItem();

            if (ipInfo != null) {
                informationController.setTextFieldIPAddress(ipInfo.getIpAddress());
                informationController.setTextFieldCountry(ipInfo.getCountry());
                informationController.setTextFieldRegion(ipInfo.getRegion());
                informationController.setTextFieldCity(ipInfo.getCity());

                if (ipInfo.isWhois()) {
                    informationController.setTextFieldNetwork(ipInfo.getNetwork());
                    informationController.setTextFieldOwner(ipInfo.getOwner());
                }

                informationController.setTextFieldOther(ipInfo.getOther());

                informationView.show(getStage());
            }
        });

        contextMenu.getItems().add(item);

        informationView.getStage().setOnCloseRequest(event -> {
            informationController.setTextFieldNetwork("");
            informationController.setTextFieldOwner("");
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnNetwork.setCellValueFactory(new PropertyValueFactory<>("network"));
        columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));

        tableViewIPInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textAreaWhois.clear();

                IPInfo ipInfo = tableViewIPInfo.getSelectionModel().getSelectedItem();

                if (ipInfo != null) {
                    if (ipInfo.isWhois()) {
                        textAreaWhois.setText(ipInfo.getWhois());
                    }
                }
            }
        });

        // Задания свойств таблице (для строк)
        tableViewIPInfo.setRowFactory( tv -> {
            TableRow<IPInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> { // Обработчик нажатия на строку
                if (event.getClickCount() == 2) { // Если количество нажатий = 2
                    if (!row.isEmpty()) { // Не пустая ли строка
                        IPInfo rowData = row.getItem(); // Получение текущих данных хиз строки
                        if (!rowData.isWhois()) { // Если whois ещё небыл получен для данной строки
                            new Thread(() -> { // То запускаем отдельный поток для получения whois
                                // Отправка запроса на получение whois
                                Map<String, String> whois = new GeoIP().getWhois(rowData.getIpAddress());
                                // Запуск потока для обновления GUI
                                Platform.runLater(() -> {
                                    // Запись новых данных в строку
                                    rowData.setWhois(whois.get("whois"));
                                    rowData.setNetwork(whois.get("network"));
                                    rowData.setOwner(whois.get("owner"));
                                    rowData.setIsWhois(true);
                                    textAreaWhois.setText(rowData.getWhois()); // Запись whois
                                    tableViewIPInfo.refresh(); // Обновление данных в таблице
                                });
                            }).start();
                        }
                    }
                }
            });
            return row;
        });

        tableViewIPInfo.setOnContextMenuRequested(event -> {
            if (tableViewIPInfo.getSelectionModel().getSelectedItem() != null) {
                contextMenu.show(getStage(), event.getScreenX(), event.getScreenY());
            }
        });
    }

    @FXML
    public void eventBtnIPAddress() {
        ipAddressView.show(getStage());
    }

    @FXML
    public void eventBtnSave() {
        File file = fileChooser.showSaveDialog(getStage());

        if (file != null) {
            StringBuilder stringBuilder = new StringBuilder();

            for (IPInfo ipInfo : tableViewIPInfo.getItems()) {
                stringBuilder.append(String.format("%s (%s : %s) (%s : %s)", ipInfo.getOther(), ipInfo.getCountry(), ipInfo.getCity(), ipInfo.getNetwork(), ipInfo.getOwner()));
                stringBuilder.append("\n");
            }

            DataStream.output(file.getPath(), stringBuilder.toString());
        }
    }

    @FXML
    public void eventBtnStart() {
        ipInfoResult.clear();
        tableViewIPInfo.getItems().clear();

        GeoIP geoIP = new GeoIP();

        boolean isRussia = ipAddressController.getIsRussia();
        boolean isHMAO = ipAddressController.getIsHMAO();
        boolean isCity = ipAddressController.getIsCity();

        int count = 0;
        IPInfo ipInfo;

        for (String dataString : ipAddressController.getIPAddress().split("\n")) {
            ipInfo = geoIP.getInfo(
                    dataString,
                    isRussia,
                    isHMAO,
                    isCity
            );

            if (ipInfo != null) {
                ipInfo.setId(++count);
                ipInfo.setOther(dataString);

                ipInfoResult.add(ipInfo);
            }
        }

        if (ipInfoResult.size() > 0) {
            tableViewIPInfo.setItems(ipInfoResult);
        }
    }

    private Stage getStage() {
        return (Stage) anchorPaneMain.getScene().getWindow();
    }

}

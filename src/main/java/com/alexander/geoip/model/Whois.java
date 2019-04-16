package com.alexander.geoip.model;

import com.alexander.geoip.model.Exception.GeoIPException;

import java.io.IOException;
import java.util.Objects;

// Класс для получения whois
public class Whois {
    // Получение пути до файла WhosIP
    private static final String whoisPath = String.format("%s\\whosip.exe", System.getProperty("user.dir"));
    // Для взаимодействия с окружением приложения
    private static final Runtime runtime = Runtime.getRuntime();

    public String execCommand(String ipAddress) {
        Process process = null;
        String command = String.format("%s -r %s", whoisPath, ipAddress); // Формирование команды

        try {
            process = runtime.exec(command); // Запуск команды в процессе WhosIP
        } catch (IOException e) {
            new GeoIPException(String.format("%s (Не удается найти указанный файл)", whoisPath));
        }

        if (process != null) {
            return Objects.requireNonNull(DataStream.input(process.getInputStream())).toString().trim();
        }

        return null;
    }
}
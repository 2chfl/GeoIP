package com.alexander.geoip.model;

import java.io.*;

public class DataStream {

    public static ByteArrayOutputStream input(InputStream inputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];

            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            return byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void output(String filePath, String buffer) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(buffer.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

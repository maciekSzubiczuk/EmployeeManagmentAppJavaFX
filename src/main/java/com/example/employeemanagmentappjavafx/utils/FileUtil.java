package com.example.employeemanagmentappjavafx.utils;

import javafx.scene.image.Image;

import java.io.*;

public class FileUtil {

    public static Image inputStreamToImage(InputStream inputStream) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("photo.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] content = new byte[1024];
        int size = 0;
        while ((size = inputStream.read(content)) != -1) {
            assert outputStream != null;
            outputStream.write(content, 0, size);
        }

        assert outputStream != null;
        outputStream.close();
        inputStream.close();

        Image image = new Image("file:photo.jpg");
        return image;
    }


}

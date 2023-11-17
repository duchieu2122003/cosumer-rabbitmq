package com.portal.consumer.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCustom {

    public static boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }
}

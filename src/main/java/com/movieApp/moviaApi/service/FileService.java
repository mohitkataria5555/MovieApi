package com.movieApp.moviaApi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadFile(String path, MultipartFile file) throws IOException {
        // Normalize the path to avoid path traversal issues
        Path normalizedPath = Paths.get(path).normalize();

        // Ensure the directory exists
        Files.createDirectories(normalizedPath);

        // Save the file to the specified directory
        Path filePath = normalizedPath.resolve(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(new File(filePath.toString()))) {
            fos.write(file.getBytes());
        }

        return file.getOriginalFilename();
    }

    public InputStream getResourceUrl(String path, String fileName) throws IOException {
        // Normalize the path to avoid path traversal issues
        Path normalizedPath = Paths.get(path).normalize();

        // Get the full file path
        Path filePath = normalizedPath.resolve(fileName);

        // Ensure the file exists
        if (Files.exists(filePath)) {
            return Files.newInputStream(filePath);
        } else {
            throw new IOException("File not found: " + fileName);
        }
    }
}

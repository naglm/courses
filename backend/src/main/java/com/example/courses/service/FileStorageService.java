package com.example.courses.service;

import com.example.courses.entities.DBFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    DBFile storeFile(MultipartFile file);
    DBFile getFile(String fileId);
    byte[] getFileContent(String fileId) throws IOException;
}
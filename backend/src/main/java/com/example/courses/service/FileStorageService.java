package com.example.courses.service;

import com.example.courses.entities.DBFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public DBFile storeFile(MultipartFile file);
    public DBFile getFile(String fileId);
}

package com.example.courses.service;

import com.example.courses.entities.DBFile;
import com.example.courses.exception.FileStorageException;
import com.example.courses.exception.MyFileNotFoundException;
import com.example.courses.repository.jpa.DBFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private DBFileRepository dbFileRepository;

    public FileStorageServiceImpl(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    @Override
    public DBFile storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(filename.contains("..")) {
                throw new FileStorageException("Invalid filename: " + filename);
            }

            DBFile dbFile = new DBFile(filename, file.getContentType(), file.getBytes());
            return dbFileRepository.save(dbFile);
        }
        catch (IOException e) {
            throw new FileStorageException("Could not store file " + filename);
        }
    }

    @Override
    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}

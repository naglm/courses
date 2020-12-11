package com.example.courses.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.example.courses.entities.DBFile;
import com.example.courses.exception.MyFileNotFoundException;
import com.example.courses.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private static final Logger logger =  LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = fileStorageService.storeFile(file);
        String fileDownloadUri = linkTo(methodOn(FileController.class).downloadFile(dbFile.getId())).toString();

        return "OK file has been uploaded. Download URL: " + fileDownloadUri;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity downloadFile(@PathVariable String fileId) {

        try {
            // DB record for file
            DBFile file = fileStorageService.getFile(fileId);

            // File contents from S3
            byte[] fileContent;
            try {
                fileContent = fileStorageService.getFileContent(fileId);
            }
            catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(new ByteArrayResource(fileContent) /*new ByteArrayResource(file.getData())*/);
        } catch (MyFileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity handleFileNotFound(MyFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}

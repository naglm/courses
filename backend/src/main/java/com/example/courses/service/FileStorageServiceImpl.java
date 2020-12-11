package com.example.courses.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.example.courses.entities.DBFile;
import com.example.courses.exception.FileStorageException;
import com.example.courses.exception.MyFileNotFoundException;
import com.example.courses.repository.jpa.DBFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    private DBFileRepository dbFileRepository;
    private AmazonS3 amazonS3;

    public FileStorageServiceImpl(DBFileRepository dbFileRepository, AmazonS3 amazonS3) {
        this.dbFileRepository = dbFileRepository;
        this.amazonS3 = amazonS3;
    }

    @Override
    public DBFile storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(filename.contains("..")) {
                throw new FileStorageException("Invalid filename: " + filename);
            }

            DBFile savedDbFile = dbFileRepository.save(new DBFile(filename, file.getContentType(), /*file.getBytes()*/ null));
            amazonS3.putObject(bucketName, "files/" + savedDbFile.getId(), convertMultiPartToFile(file));
            return savedDbFile;

        }
        catch (IOException e) {
            throw new FileStorageException("Could not store file " + filename);
        }
    }

    @Override
    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    @Override
    public byte[] getFileContent(String fileId) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, "files/" + fileId);
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

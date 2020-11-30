package com.example.courses.repository.jpa;

import com.example.courses.entities.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBFileRepository extends JpaRepository<DBFile, String> {

}

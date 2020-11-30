package com.example.courses.repository.jpa;

import com.example.courses.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> findByFirstNameAndLastName(String firstName, String lastName);
}

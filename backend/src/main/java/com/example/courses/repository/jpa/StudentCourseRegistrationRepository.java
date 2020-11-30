package com.example.courses.repository.jpa;

import com.example.courses.entities.Course;
import com.example.courses.entities.StudentCourseRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentCourseRegistrationRepository extends JpaRepository<StudentCourseRegistration, Long> {

    @Query("select scr.course from StudentCourseRegistration scr where scr.student.id = :studentId")
    public Page<Course> findRegisteredCourses(Long studentId, Pageable page);
}

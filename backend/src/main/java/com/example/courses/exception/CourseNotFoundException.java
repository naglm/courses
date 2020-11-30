package com.example.courses.exception;

public class CourseNotFoundException extends  RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}

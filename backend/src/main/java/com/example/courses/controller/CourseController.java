package com.example.courses.controller;

import com.example.courses.entities.Course;
import com.example.courses.repository.jpa.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public Page<EntityModel<Course>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size)
    {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> pageResult = courseRepository.findAll(pageRequest);
        List<EntityModel<Course>> courses = pageResult
            .stream()
            .map(course -> EntityModel.of(course))
            .collect(Collectors.toList());

        return new PageImpl<>(courses, pageRequest, pageResult.getTotalElements());
    }
}

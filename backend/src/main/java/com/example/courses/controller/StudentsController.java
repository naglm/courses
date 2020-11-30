package com.example.courses.controller;

import com.example.courses.entities.Course;
import com.example.courses.entities.Student;
import com.example.courses.entities.StudentCourseRegistration;
import com.example.courses.exception.CourseNotFoundException;
import com.example.courses.exception.StudentNotFoundException;
import com.example.courses.model.StudentCourseRegForm;
import com.example.courses.repository.jpa.CourseRepository;
import com.example.courses.repository.jpa.StudentCourseRegistrationRepository;
import com.example.courses.repository.jpa.StudentRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController("Students")
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@OpenAPIDefinition(info = @Info(title = "Students"))
public class StudentsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private StudentCourseRegistrationRepository studentCourseRegRepo;

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<Page<EntityModel<Student>>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            HttpServletRequest request
    )
    {
        Sort sort = null;
        if(sortBy != null) {
            sort = "desc".equals(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy);
        }
        PageRequest paging = sort != null ? PageRequest.of(page, size, sort) : PageRequest.of(page, size);
        Page<EntityModel<Student>> result = studentRepository.findAll(paging)
                .map(student -> EntityModel.of(
                        student,
                        linkTo(methodOn(StudentsController.class).getOne(student.getId())).withSelfRel(),
                        linkTo(methodOn(StudentsController.class).getStudentRegistrations(student.getId(), null)).withRel("registrations")));
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get students by id")
    public ResponseEntity<Student> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id)));
    }

    @PostMapping
    @Operation(summary = "Create new Student")
    public ResponseEntity save(@RequestBody Student student) {
        Student s = studentRepository.save(student);
        return ResponseEntity.created(linkTo(methodOn(StudentsController.class).getOne(student.getId())).toUri()).build();
    }

    @GetMapping(path = "/{id}/registrations")
    @Operation(summary = "Get course registrations for Student with given ID")
    public ResponseEntity<Page<EntityModel<Course>>> getStudentRegistrations(@PathVariable Long id, Pageable page) {
        Page<EntityModel<Course>> courses = studentCourseRegRepo
                .findRegisteredCourses(id, page)
                .map(course -> EntityModel.of(
                        course
                        //  , linkTo(methodOn(CourseController.class).getOne(course.getId)).withSelRel()    // when we add CourseController then add this link
                        ));
        return ResponseEntity.ok(courses);
    }

    @PostMapping(path = "/{id}/registrations")
    @Operation(summary = "Create new course registrations for Student with given ID")
    public ResponseEntity registerStudentForCourse(@PathVariable Long id, @RequestBody StudentCourseRegForm form) throws URISyntaxException {
        Course course = courseRepository.findByCode(form.getCourseCode()).orElseThrow(() -> new CourseNotFoundException("Course not found with code: " + form.getCourseCode()));
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        StudentCourseRegistration scr = StudentCourseRegistration.builder()
                .course(course)
                .student(student)
                .build();

        StudentCourseRegistration savedReg = studentCourseRegRepo.save(scr);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, "http://www.here-will-be-some-link.com").build();
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete student by ID")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        try {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

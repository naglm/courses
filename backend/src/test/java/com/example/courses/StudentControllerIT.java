package com.example.courses;

import com.example.courses.entities.Student;
import com.example.courses.repository.jpa.StudentRepository;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(CoursesApplication.PROFILE_TEST)
public class StudentControllerIT {
    @LocalServerPort
    int port;

    private String baseURL;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void beforeEach() {
        this.baseURL = "http://localhost:" + port + "/api/v1";
    }

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    public void test_getStudent_nameIsCorrect() throws Exception {
        Student savedStudent = studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));

        ResponseEntity<String> response = template
                .withBasicAuth("admin", "admin")
                .getForEntity(baseURL + "/students/" + savedStudent.getId(), String.class);

        assertTrue(response.getBody().contains("John"));
        String responseBody = response.getBody();
        JSONAssert.assertEquals("{\"firstName\":\"John\",\"lastName\":\"Student\"}", response.getBody(), false);
    }

    @Test
    public void test_createStudent_recordIsSaved() throws Exception {
        JSONObject student = new JSONObject();
        student.put("firstName", "Tom12345");
        student.put("lastName", "Cat67890");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<String>(student.toString(), headers);

        ResponseEntity<String> response = template.postForEntity(baseURL + "/students/", request, String.class);

        List<Student> students = studentRepository.findByFirstNameAndLastName("Tom12345", "Cat67890");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, students.size());
    }

    @SneakyThrows
    @Test
    public void test_paging() {
        studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));
        studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));
        studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));
        studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));
        studentRepository.save(new Student(null, "John", "Student", LocalDate.of(2020,1,1), "email@example.com", new ArrayList<>()));

        ResponseEntity<String> response = template.getForEntity(baseURL + "/students?page=0&size=3", String.class);
        JSONAssert.assertEquals("{\"numberOfElements\":3}", response.getBody(), false);

        response = template.getForEntity(baseURL + "/students?page=0&size=10", String.class);
        JSONAssert.assertEquals("{\"numberOfElements\":5}", response.getBody(), false);

        response = template.getForEntity(baseURL + "/students?page=2&size=10", String.class);
        JSONAssert.assertEquals("{\"numberOfElements\":0}", response.getBody(), false);

    }
}

package com.example.courses;

import com.example.courses.entities.Student;
import com.example.courses.repository.jpa.StudentRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(CoursesApplication.PROFILE_TEST)
public class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentRepository studentRepository;

    @SneakyThrows
    @Test
    public void test_getStudents() {
        Pageable pageable = PageRequest.of(0, 20);  //values must match

        List<Student> students = Arrays.asList(
                new Student(1L, "s1", "s1", null, "email@example.com", new ArrayList<>()),
                new Student(2L, "s2", "s2", null, "email@example.com", new ArrayList<>()),
                new Student(3L, "s3", "s3", null, "email@example.com", new ArrayList<>())
        );

        when(studentRepository.findAll(pageable)).thenReturn(new PageImpl<>(students, pageable, 3));

        this.mvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(3)))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].firstName", is("s1")));
    }
}

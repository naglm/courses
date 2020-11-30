package com.example.courses;

import com.example.courses.entities.Employee;
import com.example.courses.repository.jpa.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployee_ok() throws Exception {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(new Employee("Johnny Bravo", "admin")));
        //the same: when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee("Johnny Bravo", "admin")));

        this.mvc.perform(get("/api/v1/employees/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Johnny Bravo")))
                .andExpect(jsonPath("$.role", is("admin")));

    }

    @Test
    public void testGetEmployee_notFound() throws Exception {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee("Johnny Bravo", "admin")));

        this.mvc.perform(get("/api/v1/employees/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}

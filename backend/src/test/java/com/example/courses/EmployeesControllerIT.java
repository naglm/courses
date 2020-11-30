package com.example.courses;

import com.example.courses.entities.Employee;
import com.example.courses.repository.jpa.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeesControllerIT {

	@LocalServerPort
	private int port;

	private URL baseUrl;

	@Autowired
	private TestRestTemplate template;

    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/employees/");
    }

    @Test
    public void getHello() throws Exception {
        //given(employeeRepository.findById(1L)).willReturn(Optional.of(new Employee("Johnny Bravo", "admin")));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee("Johnny Bravo", "admin")));

        ResponseEntity<String> response = template.getForEntity(baseUrl.toString() + "/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONAssert.assertEquals("{\"name\":\"Johnny Bravo\"}", response.getBody(), false);
        JSONAssert.assertEquals("{\"role\":\"admin\"}", response.getBody(), false);
    }
}

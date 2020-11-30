package com.example.courses.controller;

import com.example.courses.repository.jpa.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employees_search/")
public class EmployeeSearchController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(path = "/managers")
    public ResponseEntity getAllManagers() {
        return ResponseEntity.ok(CollectionModel.of(employeeRepository.findAllManagers()));
    }

    @GetMapping(path = "/search")
    public ResponseEntity searchEmployees(@RequestParam String name, @RequestParam String role) {
        return ResponseEntity.ok(CollectionModel.of(employeeRepository.findEmployees(name, role, Sort.by(Sort.Order.asc("name")))));
    }
}

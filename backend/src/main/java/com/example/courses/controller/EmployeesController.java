package com.example.courses.controller;

import com.example.courses.entities.Employee;
import com.example.courses.exception.EmployeeNotFoundException;
import com.example.courses.model.EmployeeModelAssembler;
import com.example.courses.repository.jpa.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/v1/employees/")
public class EmployeesController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler modelAssembler;

    public EmployeesController(EmployeeRepository employeeRepository, EmployeeModelAssembler modelAssembler) {
        this.employeeRepository = employeeRepository;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<EntityModel<Employee>> employees = employeeRepository
                .findAll()
                .stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(employees, linkTo(methodOn(EmployeesController.class).findAll()).withSelfRel()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable(name = "id") Long id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id.toString()));
        return ResponseEntity.ok(modelAssembler.toModel(employee));
    }
}

package com.example.courses.repository.jpa;


import com.example.courses.entities.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.role = 'manager'")
    public Iterable<Employee> findAllManagers();

    @Query("SELECT e FROM Employee e WHERE e.name = :name and e.role = :role")
    public Iterable<Employee> findEmployees(String name, String role, Sort sort);

    @Query(value = "SELECT * FROM employees e WHERE e.role = 'manager'", nativeQuery = true)
    public Iterable<Employee> findAllManagersNative();
}

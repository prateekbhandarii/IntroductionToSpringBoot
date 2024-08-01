package com.pratik.example.introduction.controller;

import com.pratik.example.introduction.dto.EmployeeDTO;
import com.pratik.example.introduction.entity.EmployeeEntity;
import com.pratik.example.introduction.repository.EmployeeRepository;
import com.pratik.example.introduction.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{empId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long empId) {
        return employeeService.getEmployeeById(empId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO entity) {
        return employeeService.createEmployee(entity);
    }
}

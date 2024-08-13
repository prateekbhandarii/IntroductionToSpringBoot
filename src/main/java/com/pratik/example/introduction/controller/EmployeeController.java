package com.pratik.example.introduction.controller;

import com.pratik.example.introduction.dto.EmployeeDTO;
import com.pratik.example.introduction.exceptions.ResourceNotFoundException;
import com.pratik.example.introduction.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{empId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long empId) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(empId);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + empId));
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEmployeeNotFoundException(NoSuchElementException exception) {
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO entity) {
        EmployeeDTO employeeDTO = employeeService.createEmployee(entity);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO entity, @PathVariable Long empId) {
        return ResponseEntity.ok(employeeService.updateEmployee(empId, entity));
    }

    @DeleteMapping(path = "/{empId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long empId) {
        boolean gotDeleted = employeeService.deleteEmployeeById(empId);
        if (gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployeePartially(@RequestBody Map<String, Object> changeMap, @PathVariable Long empId) {
        EmployeeDTO employeeDTO = employeeService.updateEmployeePartially(empId, changeMap);
        if (employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}

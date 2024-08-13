package com.pratik.example.introduction.service;

import com.pratik.example.introduction.dto.EmployeeDTO;
import com.pratik.example.introduction.entity.EmployeeEntity;
import com.pratik.example.introduction.exceptions.ResourceNotFoundException;
import com.pratik.example.introduction.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> mapper.map(employeeEntity1, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> list = employeeRepository.findAll();
        return list.stream().map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO entity) {
        EmployeeEntity originalEntity = mapper.map(entity, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(originalEntity);
        return mapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long empId, EmployeeDTO entity) {
        checkIfEmployeeExists(empId);
        EmployeeEntity employeeEntity = mapper.map(entity, EmployeeEntity.class);
        employeeEntity.setId(empId);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployee, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long empId) {
        checkIfEmployeeExists(empId);
        employeeRepository.deleteById(empId);
        return true;
    }

    public EmployeeDTO updateEmployeePartially(Long empId, Map<String, Object> changeMap) {
        checkIfEmployeeExists(empId);
        EmployeeEntity employeeEntity = employeeRepository.findById(empId).get();
        changeMap.forEach((key, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        return mapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    public void checkIfEmployeeExists(Long empId) {
        boolean existById = employeeRepository.existsById(empId);
        if (!existById) throw new ResourceNotFoundException("Employee not found with id: " + empId);
    }
}

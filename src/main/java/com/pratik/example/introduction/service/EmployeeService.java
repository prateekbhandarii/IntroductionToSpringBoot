package com.pratik.example.introduction.service;

import com.pratik.example.introduction.dto.EmployeeDTO;
import com.pratik.example.introduction.entity.EmployeeEntity;
import com.pratik.example.introduction.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return mapper.map(employeeEntity, EmployeeDTO.class);
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
}

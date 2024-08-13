package com.pratik.example.introduction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pratik.example.introduction.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    @NotBlank(message = "Name cannot be null or empty.")
    @Size(min = 3, max = 10, message = "Name should be in range from 3 to 10")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Max(value = 80, message = "Age cannot be greater than 80")
    @Min(value = 18, message = "Age cannot be lesser than 18")
    private Integer age;

    @NotBlank(message = "Role cannot be blank")
    //@Pattern(regexp = "^(ADMIN|USER)$", message = "Role can either be ADMIN or USER only.")
    @EmployeeRoleValidation
    private String role;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate dateOfJoining;

    @JsonProperty("isActive")
    private Boolean isActive;
}

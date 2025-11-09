package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotBlank(message = "First name is required")
    @Size(max = 25, message = "First name must not exceed 25 characters")
    private String firstName;
    
    @Size(max = 25, message = "Middle name must not exceed 25 characters")
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 25, message = "Last name must not exceed 25 characters")
    private String lastName;
    
    @NotNull(message = "SSN is required")
    private Long ssn;
    
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 50, message = "Address line 1 must not exceed 50 characters")
    private String addressLine1;
    
    @Size(max = 50, message = "Address line 2 must not exceed 50 characters")
    private String addressLine2;
    
    @Size(max = 50, message = "Address line 3 must not exceed 50 characters")
    private String addressLine3;
    
    @NotBlank(message = "State code is required")
    @Size(max = 2, message = "State code must not exceed 2 characters")
    private String stateCode;
    
    @NotBlank(message = "Country code is required")
    @Size(max = 3, message = "Country code must not exceed 3 characters")
    private String countryCode;
    
    @NotBlank(message = "ZIP code is required")
    @Size(max = 10, message = "ZIP code must not exceed 10 characters")
    private String zipCode;
    
    @NotNull(message = "FICO credit score is required")
    @Min(value = 300, message = "FICO credit score must be at least 300")
    @Max(value = 850, message = "FICO credit score must not exceed 850")
    private Integer ficoCreditScore;
}

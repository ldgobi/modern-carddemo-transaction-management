package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReportRequestDTO {
    
    @NotBlank(message = "Report type is required")
    @Pattern(regexp = "^(MONTHLY|YEARLY|CUSTOM)$", message = "Report type must be MONTHLY, YEARLY, or CUSTOM")
    private String reportType;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    @NotBlank(message = "Confirmation is required")
    @Pattern(regexp = "^[YyNn]$", message = "Confirmation must be Y or N")
    private String confirmation;
}

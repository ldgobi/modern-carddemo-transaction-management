package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    
    @NotNull(message = "Account ID is required")
    private Long accountId;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotBlank(message = "Active status is required")
    @Pattern(regexp = "^[YN]$", message = "Active status must be Y or N")
    private String activeStatus;
    
    @NotNull(message = "Current balance is required")
    @Digits(integer = 10, fraction = 2, message = "Current balance must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentBalance;
    
    @NotNull(message = "Credit limit is required")
    @Digits(integer = 10, fraction = 2, message = "Credit limit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal creditLimit;
    
    @NotNull(message = "Cash credit limit is required")
    @Digits(integer = 10, fraction = 2, message = "Cash credit limit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal cashCreditLimit;
    
    @NotNull(message = "Open date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;
    
    @NotNull(message = "Expiration date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    
    @NotNull(message = "Current cycle credit is required")
    @Digits(integer = 10, fraction = 2, message = "Current cycle credit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentCycleCredit;
    
    @NotNull(message = "Current cycle debit is required")
    @Digits(integer = 10, fraction = 2, message = "Current cycle debit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentCycleDebit;
}

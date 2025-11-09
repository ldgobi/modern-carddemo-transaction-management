package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    
    @NotBlank(message = "Transaction ID is required")
    @Size(max = 16, message = "Transaction ID must not exceed 16 characters")
    private String transactionId;
    
    @NotBlank(message = "Card number is required")
    @Size(max = 16, message = "Card number must not exceed 16 characters")
    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be 16 digits")
    private String cardNumber;
    
    @NotNull(message = "Account ID is required")
    private Long accountId;
    
    @NotBlank(message = "Type code is required")
    @Size(max = 2, message = "Type code must not exceed 2 characters")
    private String typeCode;
    
    @NotNull(message = "Category code is required")
    @Min(value = 0, message = "Category code must be positive")
    @Max(value = 9999, message = "Category code must not exceed 9999")
    private Integer categoryCode;
    
    @NotBlank(message = "Source is required")
    @Size(max = 10, message = "Source must not exceed 10 characters")
    private String source;
    
    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;
    
    @NotNull(message = "Amount is required")
    @Digits(integer = 9, fraction = 2, message = "Amount must have at most 9 integer digits and 2 decimal places")
    private BigDecimal amount;
    
    @NotNull(message = "Merchant ID is required")
    private Long merchantId;
    
    @NotBlank(message = "Merchant name is required")
    @Size(max = 50, message = "Merchant name must not exceed 50 characters")
    private String merchantName;
    
    @NotBlank(message = "Merchant city is required")
    @Size(max = 50, message = "Merchant city must not exceed 50 characters")
    private String merchantCity;
    
    @NotBlank(message = "Merchant ZIP is required")
    @Size(max = 10, message = "Merchant ZIP must not exceed 10 characters")
    private String merchantZip;
    
    @NotNull(message = "Original timestamp is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime originalTimestamp;
    
    @NotNull(message = "Processed timestamp is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime processedTimestamp;
}

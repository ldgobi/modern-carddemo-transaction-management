package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListDTO {
    
    private String transactionId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime transactionDate;
    
    private String description;
    
    private BigDecimal amount;
}

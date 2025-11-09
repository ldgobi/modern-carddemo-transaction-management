package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    
    @Id
    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;
    
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "cvv_code", nullable = false, length = 3)
    private String cvvCode;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

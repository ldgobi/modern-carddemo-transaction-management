package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;
    
    @Column(name = "middle_name", length = 25)
    private String middleName;
    
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;
    
    @Column(name = "ssn", nullable = false)
    private Long ssn;
    
    @Column(name = "address_line_1", nullable = false, length = 50)
    private String addressLine1;
    
    @Column(name = "address_line_2", length = 50)
    private String addressLine2;
    
    @Column(name = "address_line_3", length = 50)
    private String addressLine3;
    
    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;
    
    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;
    
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;
    
    @Column(name = "fico_credit_score", nullable = false)
    private Integer ficoCreditScore;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

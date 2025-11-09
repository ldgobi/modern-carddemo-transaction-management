package com.example.demo.controller;

import com.example.demo.dto.TransactionCreateDTO;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.dto.TransactionListDTO;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    
    private final TransactionService transactionService;
    
    /**
     * Get a transaction by ID
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<Map<String, Object>> getTransaction(@PathVariable String transactionId) {
        log.info("GET /api/transactions/{}", transactionId);
        try {
            TransactionDTO transaction = transactionService.getTransactionById(transactionId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transaction);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error fetching transaction: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * List transactions with pagination
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /api/transactions?page={}&size={}", page, size);
        try {
            Page<TransactionListDTO> transactions = transactionService.listTransactions(page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transactions.getContent());
            response.put("currentPage", transactions.getNumber());
            response.put("totalPages", transactions.getTotalPages());
            response.put("totalItems", transactions.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error listing transactions: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Create a new transaction
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransaction(@Valid @RequestBody TransactionCreateDTO createDTO) {
        log.info("POST /api/transactions");
        try {
            TransactionDTO transaction = transactionService.createTransaction(createDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Transaction created successfully with ID: " + transaction.getTransactionId());
            response.put("data", transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            log.error("Error creating transaction: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Get transactions by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<Map<String, Object>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/transactions/date-range?startDate={}&endDate={}", startDate, endDate);
        try {
            List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transactions);
            response.put("count", transactions.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching transactions by date range: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get transactions by card number
     */
    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<Map<String, Object>> getTransactionsByCardNumber(@PathVariable String cardNumber) {
        log.info("GET /api/transactions/card/{}", cardNumber);
        try {
            List<TransactionDTO> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transactions);
            response.put("count", transactions.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching transactions by card number: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

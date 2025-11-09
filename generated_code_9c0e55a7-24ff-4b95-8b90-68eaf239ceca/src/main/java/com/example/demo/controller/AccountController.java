package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    
    private final AccountService accountService;
    
    /**
     * Get an account by ID
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable Long accountId) {
        log.info("GET /api/accounts/{}", accountId);
        try {
            AccountDTO account = accountService.getAccountById(accountId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", account);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error fetching account: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Get all accounts
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAccounts() {
        log.info("GET /api/accounts");
        try {
            List<AccountDTO> accounts = accountService.getAllAccounts();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", accounts);
            response.put("count", accounts.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching accounts: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get accounts by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Map<String, Object>> getAccountsByCustomer(@PathVariable Long customerId) {
        log.info("GET /api/accounts/customer/{}", customerId);
        try {
            List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", accounts);
            response.put("count", accounts.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching accounts by customer: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

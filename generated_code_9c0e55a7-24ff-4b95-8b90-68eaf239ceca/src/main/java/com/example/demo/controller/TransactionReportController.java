package com.example.demo.controller;

import com.example.demo.dto.TransactionReportRequestDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class TransactionReportController {
    
    private final TransactionReportService reportService;
    
    /**
     * Generate transaction report
     */
    @PostMapping("/transactions")
    public ResponseEntity<Map<String, Object>> generateTransactionReport(
            @Valid @RequestBody TransactionReportRequestDTO request) {
        log.info("POST /api/reports/transactions - type: {}", request.getReportType());
        try {
            List<Transaction> transactions = reportService.generateReport(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", request.getReportType() + " report generated successfully");
            response.put("reportType", request.getReportType());
            response.put("transactionCount", transactions.size());
            response.put("data", transactions);
            
            if ("CUSTOM".equals(request.getReportType())) {
                response.put("startDate", request.getStartDate());
                response.put("endDate", request.getEndDate());
            }
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error generating report: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

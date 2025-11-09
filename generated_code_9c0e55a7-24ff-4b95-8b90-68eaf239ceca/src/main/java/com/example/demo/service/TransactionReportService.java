package com.example.demo.service;

import com.example.demo.dto.TransactionReportRequestDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionReportService {
    
    private final TransactionRepository transactionRepository;
    private final DateValidationService dateValidationService;
    
    @Transactional(readOnly = true)
    public List<Transaction> generateReport(TransactionReportRequestDTO request) {
        log.info("Generating {} transaction report", request.getReportType());
        
        // Validate confirmation
        if (!request.getConfirmation().equalsIgnoreCase("Y")) {
            throw new RuntimeException("Confirm to generate this report...");
        }
        
        LocalDate startDate;
        LocalDate endDate;
        
        switch (request.getReportType().toUpperCase()) {
            case "MONTHLY":
                YearMonth currentMonth = YearMonth.now();
                startDate = currentMonth.atDay(1);
                endDate = currentMonth.atEndOfMonth();
                log.info("Monthly report: {} to {}", startDate, endDate);
                break;
                
            case "YEARLY":
                int currentYear = LocalDate.now().getYear();
                startDate = LocalDate.of(currentYear, 1, 1);
                endDate = LocalDate.of(currentYear, 12, 31);
                log.info("Yearly report: {} to {}", startDate, endDate);
                break;
                
            case "CUSTOM":
                if (request.getStartDate() == null || request.getEndDate() == null) {
                    throw new RuntimeException("Start date and end date are required for custom reports");
                }
                
                // Validate dates
                String dateFormat = "yyyy-MM-dd";
                if (!dateValidationService.validateDate(request.getStartDate().toString(), dateFormat)) {
                    throw new RuntimeException("Invalid start date format...");
                }
                if (!dateValidationService.validateDate(request.getEndDate().toString(), dateFormat)) {
                    throw new RuntimeException("Invalid end date format...");
                }
                
                startDate = request.getStartDate();
                endDate = request.getEndDate();
                
                if (startDate.isAfter(endDate)) {
                    throw new RuntimeException("Start date must be before or equal to end date");
                }
                
                log.info("Custom report: {} to {}", startDate, endDate);
                break;
                
            default:
                throw new RuntimeException("Invalid report type. Valid values are MONTHLY, YEARLY, or CUSTOM");
        }
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<Transaction> transactions = transactionRepository.findByDateRange(startDateTime, endDateTime);
        log.info("Report generated with {} transactions", transactions.size());
        
        return transactions;
    }
}

package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
public class DateValidationService {
    
    /**
     * Validates a date string against a specified format
     * Based on CSUTLDTC.cbl date validation utility
     * 
     * @param dateString The date string to validate
     * @param format The expected format (e.g., "yyyy-MM-dd")
     * @return true if the date is valid, false otherwise
     */
    public boolean validateDate(String dateString, String format) {
        log.debug("Validating date: {} with format: {}", dateString, format);
        
        if (dateString == null || dateString.trim().isEmpty()) {
            log.warn("Date validation failed: Insufficient data");
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate.parse(dateString, formatter);
            log.debug("Date validation successful for: {}", dateString);
            return true;
        } catch (DateTimeParseException e) {
            log.warn("Date validation failed for: {} - {}", dateString, e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.warn("Invalid date format pattern: {} - {}", format, e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates a date string with default format yyyy-MM-dd
     * 
     * @param dateString The date string to validate
     * @return true if the date is valid, false otherwise
     */
    public boolean validateDate(String dateString) {
        return validateDate(dateString, "yyyy-MM-dd");
    }
    
    /**
     * Validates that a date is within a valid range
     * 
     * @param dateString The date string to validate
     * @param format The expected format
     * @param minDate The minimum allowed date
     * @param maxDate The maximum allowed date
     * @return true if the date is valid and within range, false otherwise
     */
    public boolean validateDateInRange(String dateString, String format, LocalDate minDate, LocalDate maxDate) {
        if (!validateDate(dateString, format)) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate date = LocalDate.parse(dateString, formatter);
            
            boolean inRange = !date.isBefore(minDate) && !date.isAfter(maxDate);
            if (!inRange) {
                log.warn("Date {} is outside valid range [{}, {}]", dateString, minDate, maxDate);
            }
            return inRange;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

package com.example.demo.service;

import com.example.demo.dto.TransactionCreateDTO;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.dto.TransactionListDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final TransactionCategoryBalanceRepository categoryBalanceRepository;
    private final DateValidationService dateValidationService;
    
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(String transactionId) {
        log.info("Fetching transaction with ID: {}", transactionId);
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction ID NOT found..."));
        return mapToDTO(transaction);
    }
    
    @Transactional(readOnly = true)
    public Page<TransactionListDTO> listTransactions(int page, int size) {
        log.info("Listing transactions - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAllByOrderByOriginalTimestampDesc(pageable);
        return transactions.map(this::mapToListDTO);
    }
    
    @Transactional
    public TransactionDTO createTransaction(TransactionCreateDTO createDTO) {
        log.info("Creating new transaction for card: {}", createDTO.getCardNumber());
        
        // Validate confirmation
        if (!createDTO.getConfirmation().equalsIgnoreCase("Y")) {
            throw new RuntimeException("Confirm to add this transaction...");
        }
        
        // Validate card number
        Card card = cardRepository.findById(createDTO.getCardNumber())
                .orElseThrow(() -> new RuntimeException("Card Number NOT found..."));
        
        // Validate account
        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account ID NOT found..."));
        
        // Validate dates
        String dateFormat = "yyyy-MM-dd";
        String originalDateStr = createDTO.getOriginalTimestamp().toLocalDate().toString();
        
        if (!dateValidationService.validateDate(originalDateStr, dateFormat)) {
            throw new RuntimeException("Invalid original date format...");
        }
        
        // Check if transaction date is before account expiration
        if (createDTO.getOriginalTimestamp().toLocalDate().isAfter(account.getExpirationDate())) {
            throw new RuntimeException("Transaction received after account expiration...");
        }
        
        // Check credit limit
        BigDecimal tempBalance = account.getCurrentCycleCredit()
                .subtract(account.getCurrentCycleDebit())
                .add(createDTO.getAmount());
        
        if (tempBalance.compareTo(account.getCreditLimit()) > 0) {
            throw new RuntimeException("Overlimit transaction...");
        }
        
        // Generate transaction ID
        String transactionId = generateTransactionId();
        
        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setCardNumber(createDTO.getCardNumber());
        transaction.setAccountId(card.getAccountId());
        transaction.setTypeCode(createDTO.getTypeCode());
        transaction.setCategoryCode(createDTO.getCategoryCode());
        transaction.setSource(createDTO.getSource());
        transaction.setDescription(createDTO.getDescription());
        transaction.setAmount(createDTO.getAmount());
        transaction.setMerchantId(createDTO.getMerchantId());
        transaction.setMerchantName(createDTO.getMerchantName());
        transaction.setMerchantCity(createDTO.getMerchantCity());
        transaction.setMerchantZip(createDTO.getMerchantZip());
        transaction.setOriginalTimestamp(createDTO.getOriginalTimestamp());
        transaction.setProcessedTimestamp(LocalDateTime.now());
        
        transaction = transactionRepository.save(transaction);
        
        // Update account balances
        updateAccountBalances(account, createDTO.getAmount());
        
        // Update category balances
        updateCategoryBalances(card.getAccountId(), createDTO.getTypeCode(), 
                createDTO.getCategoryCode(), createDTO.getAmount());
        
        log.info("Transaction created successfully with ID: {}", transactionId);
        return mapToDTO(transaction);
    }
    
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching transactions from {} to {}", startDate, endDate);
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<Transaction> transactions = transactionRepository.findByDateRange(startDateTime, endDateTime);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByCardNumber(String cardNumber) {
        log.info("Fetching transactions for card: {}", cardNumber);
        List<Transaction> transactions = transactionRepository.findByCardNumberOrderByOriginalTimestampDesc(cardNumber);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    private String generateTransactionId() {
        // Get last transaction and increment ID
        List<Transaction> lastTransaction = transactionRepository.findAll(PageRequest.of(0, 1)).getContent();
        if (lastTransaction.isEmpty()) {
            return String.format("%016d", 1L);
        }
        
        try {
            long lastId = Long.parseLong(lastTransaction.get(0).getTransactionId());
            return String.format("%016d", lastId + 1);
        } catch (NumberFormatException e) {
            // If parsing fails, generate UUID-based ID
            return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        }
    }
    
    private void updateAccountBalances(Account account, BigDecimal amount) {
        account.setCurrentBalance(account.getCurrentBalance().add(amount));
        
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            account.setCurrentCycleCredit(account.getCurrentCycleCredit().add(amount));
        } else {
            account.setCurrentCycleDebit(account.getCurrentCycleDebit().add(amount.abs()));
        }
        
        accountRepository.save(account);
    }
    
    private void updateCategoryBalances(Long accountId, String typeCode, Integer categoryCode, BigDecimal amount) {
        TransactionCategoryBalance.TransactionCategoryBalanceId id = 
                new TransactionCategoryBalance.TransactionCategoryBalanceId(accountId, typeCode, categoryCode);
        
        TransactionCategoryBalance balance = categoryBalanceRepository.findById(id)
                .orElse(new TransactionCategoryBalance(accountId, typeCode, categoryCode, BigDecimal.ZERO, null, null));
        
        balance.setBalance(balance.getBalance().add(amount));
        categoryBalanceRepository.save(balance);
    }
    
    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setCardNumber(transaction.getCardNumber());
        dto.setAccountId(transaction.getAccountId());
        dto.setTypeCode(transaction.getTypeCode());
        dto.setCategoryCode(transaction.getCategoryCode());
        dto.setSource(transaction.getSource());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setMerchantId(transaction.getMerchantId());
        dto.setMerchantName(transaction.getMerchantName());
        dto.setMerchantCity(transaction.getMerchantCity());
        dto.setMerchantZip(transaction.getMerchantZip());
        dto.setOriginalTimestamp(transaction.getOriginalTimestamp());
        dto.setProcessedTimestamp(transaction.getProcessedTimestamp());
        return dto;
    }
    
    private TransactionListDTO mapToListDTO(Transaction transaction) {
        TransactionListDTO dto = new TransactionListDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getOriginalTimestamp());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        return dto;
    }
}

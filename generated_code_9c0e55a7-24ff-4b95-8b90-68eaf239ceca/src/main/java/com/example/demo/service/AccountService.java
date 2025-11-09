package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Long accountId) {
        log.info("Fetching account with ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account NOT found..."));
        return mapToDTO(account);
    }
    
    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
        log.info("Fetching accounts for customer ID: {}", customerId);
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        return accounts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AccountDTO> getAllAccounts() {
        log.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    private AccountDTO mapToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());
        dto.setCustomerId(account.getCustomerId());
        dto.setActiveStatus(account.getActiveStatus());
        dto.setCurrentBalance(account.getCurrentBalance());
        dto.setCreditLimit(account.getCreditLimit());
        dto.setCashCreditLimit(account.getCashCreditLimit());
        dto.setOpenDate(account.getOpenDate());
        dto.setExpirationDate(account.getExpirationDate());
        dto.setCurrentCycleCredit(account.getCurrentCycleCredit());
        dto.setCurrentCycleDebit(account.getCurrentCycleDebit());
        return dto;
    }
}

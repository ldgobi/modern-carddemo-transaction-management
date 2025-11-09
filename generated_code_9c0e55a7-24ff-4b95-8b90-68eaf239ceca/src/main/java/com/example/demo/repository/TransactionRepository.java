package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    Page<Transaction> findAllByOrderByOriginalTimestampDesc(Pageable pageable);
    
    List<Transaction> findByCardNumberOrderByOriginalTimestampDesc(String cardNumber);
    
    List<Transaction> findByAccountIdOrderByOriginalTimestampDesc(Long accountId);
    
    @Query("SELECT t FROM Transaction t WHERE t.originalTimestamp >= :startDate AND t.originalTimestamp <= :endDate ORDER BY t.originalTimestamp DESC")
    List<Transaction> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.cardNumber = :cardNumber AND t.originalTimestamp >= :startDate AND t.originalTimestamp <= :endDate ORDER BY t.originalTimestamp DESC")
    List<Transaction> findByCardNumberAndDateRange(@Param("cardNumber") String cardNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId AND t.originalTimestamp >= :startDate AND t.originalTimestamp <= :endDate ORDER BY t.originalTimestamp DESC")
    List<Transaction> findByAccountIdAndDateRange(@Param("accountId") Long accountId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

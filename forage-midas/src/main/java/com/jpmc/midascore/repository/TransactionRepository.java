package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, Long> {

    @Query(value = """
       SELECT COALESCE(
           (SELECT SUM(amount) FROM transactions WHERE recipient_id = :userId) -
           COALESCE((SELECT SUM(amount) FROM transactions WHERE sender_id = :userId), 0),
           0
       )
   """, nativeQuery = true)
    float findBalanceByUserId(@Param("userId") Long userId);
}
package com.example.tz.front.repository;

import com.example.tz.front.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findFirst100ByOrderById();

}
package com.example.tz.back.repository;

import com.example.tz.back.domain.RawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Repository
public interface RawTransactionRepository extends JpaRepository<RawTransaction,Long> {

}
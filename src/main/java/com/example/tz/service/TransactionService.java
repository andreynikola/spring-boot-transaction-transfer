package com.example.tz.service;

import com.example.tz.front.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class TransactionService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Метод блокирует транзакцию на чтение и запись
     * @param id
     * @return Transaction
     * @throws LockTimeoutException
     * @throws PessimisticLockException
     * @throws PersistenceException
     */
    @Transactional
    public Transaction lockTransaction(Long id) {
        Transaction locked = null;
        try {
            locked = entityManager.find(Transaction.class, id, LockModeType.PESSIMISTIC_WRITE);
        } catch (LockTimeoutException e) {
            log.error("lockTransaction timeout error: {}",e.getMessage());
        } catch (PessimisticLockException e) {
            log.error("lockTransaction pessimistic lock error: {}",e.getMessage());
        } catch (PersistenceException e) {
            log.error("lockTransaction error: {}",e.getMessage());
        }
        return locked;
    }

    /**
     * Метод снимает блокировки с транзакции
     * @param transaction
     */
    @Transactional
    public void unlock(Transaction transaction){
        entityManager.lock(transaction, LockModeType.NONE);
    }

}

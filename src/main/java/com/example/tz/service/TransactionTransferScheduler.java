package com.example.tz.service;

import com.example.tz.back.domain.RawTransaction;
import com.example.tz.back.repository.RawTransactionRepository;
import com.example.tz.front.domain.Transaction;
import com.example.tz.front.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class TransactionTransferScheduler {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RawTransactionRepository rawTransactionRepository;

    /**
     * Метод по расписанию получает транзакции из БД
     * @throws Exception
     */
    @Scheduled(fixedDelay = 5000)
    public void getTransactions(){
        try{
            List<Transaction> transactions = transactionRepository.findFirst100ByOrderById();
            if (transactions.size() > 0){
                log.info(transactions.size() + " transactions are ready to transfer");
                transactions.stream().forEach(transaction -> transfer(transaction));
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * Метод асинхронно обрабатывает транзакции и отправляет их в back
     * @param candidate
     * @throws Exception
     */
    @Async("transferExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transfer(Transaction candidate){
        Transaction transaction = null;
        try{
            if (candidate == null){
                log.error("Transaction is null");
                return;
            }
            transaction = transactionService.lockTransaction(candidate.getId());
            RawTransaction rawTransaction = new RawTransaction();
            rawTransaction.setAmount(transaction.getAmount());
            rawTransaction.setBin(transaction.getCard().getBin());
            rawTransaction.setCardholderName(transaction.getCard().getCardholder());
            rawTransaction.setCardId(transaction.getCard().getId());
            rawTransaction.setTime(transaction.getTime());
            rawTransactionRepository.saveAndFlush(rawTransaction);
            transactionService.unlock(transaction);
            transactionRepository.delete(transaction);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

}

package com.n26.challenges.services;

import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.dtos.AddTransactionResponse;
import com.n26.challenges.models.Transaction;
import com.n26.challenges.util.AppConfig;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TransactionServiceTest {

    private static final AppConfig APP_CONFIG = new AppConfig(10);


    private TransactionService createTransactionService() {
        return new TransactionService(APP_CONFIG);
    }

    @Test
    public void testGetEmptyTransactions() {
        TransactionService transactionService = createTransactionService();
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertTrue("Transactions collection is not empty", transactions.isEmpty());
    }

    @Test
    public void testSingleTransaction() {
        TransactionService transactionService = createTransactionService();
        AddTransactionResponse addTransactionResponse = transactionService.addTransaction(new AddTransactionRequest(12.0, System.currentTimeMillis()));
        assertNotNull("Response is null", addTransactionResponse);
        assertEquals("Response code is incorrect", 201, addTransactionResponse.getStatus());
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 1, transactions.size());
    }

    @Test
    public void testEmpyRequestTransaction() {
        TransactionService transactionService = createTransactionService();
        AddTransactionResponse addTransactionResponse = transactionService.addTransaction(new AddTransactionRequest());
        assertNotNull("Response is null", addTransactionResponse);
        assertEquals("Response code is incorrect", 204, addTransactionResponse.getStatus());
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 0, transactions.size());
    }

    @Test
    public void tesMultipleTransactions() {
        TransactionService transactionService = createTransactionService();
        transactionService.addTransaction(new AddTransactionRequest(5.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(6.0, System.currentTimeMillis()));
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 2, transactions.size());
    }

    @Test
    public void testRemoveTransactions() {
        TransactionService transactionService = createTransactionService();
        transactionService.addTransaction(new AddTransactionRequest(5.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(6.0, System.currentTimeMillis()));
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 2, transactions.size());
        transactionService.updateTransactions(transactions);
        transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 0, transactions.size());
    }

    @Test
    public void testAddOldTransactions() {
        TransactionService transactionService = createTransactionService();
        long oldTimestamp = Instant.now().minus(Duration.ofSeconds(APP_CONFIG.getMaxTransactionTimeInSeconds() + 1)).toEpochMilli();
        transactionService.addTransaction(new AddTransactionRequest(5.0, oldTimestamp));
        transactionService.addTransaction(new AddTransactionRequest(6.0, oldTimestamp));
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull("Transactions collection is null", transactions);
        assertEquals("Transaction size is not correct", 0, transactions.size());
    }
}

package com.n26.challenges.services;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.models.Transaction;
import com.n26.challenges.util.AppConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrentTransactionServiceTest {

    private AppConfig appConfig = new AppConfig(10);
    private TransactionService transactionService = new TransactionService(appConfig);

    @Test
    @ThreadCount(10)
    public void testInsertResult() {
        Double amount = 12.3;
        transactionService.addTransaction(new AddTransactionRequest(amount, System.currentTimeMillis()));
    }

    @After
    public void testConcurrentResults() {
        Collection<Transaction> transactions = transactionService.getTransactions();
        assertNotNull(transactions);
        assertEquals("Size is different than expected", 10, transactionService.getTransactions().size());
    }
}

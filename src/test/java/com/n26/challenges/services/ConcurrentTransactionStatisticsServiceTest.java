package com.n26.challenges.services;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.models.TransactionStatistics;
import com.n26.challenges.util.AppConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrentTransactionStatisticsServiceTest {

    private AppConfig appConfig = new AppConfig(10);
    private TransactionService transactionService = new TransactionService(appConfig);
    private TransactionStatisticsService transactionStatisticsService = new TransactionStatisticsService(appConfig, transactionService);

    @Test
    @ThreadCount(10)
    public void testInsertResult() {
        Double amount = 12.3;
        transactionService.addTransaction(new AddTransactionRequest(amount, System.currentTimeMillis()));
    }

    @After
    public void testConcurrentResults() {
        transactionStatisticsService.updateStatistics();
        TransactionStatistics transactionStatistics = transactionStatisticsService.getStatistics();
        assertNotNull(transactionStatistics);
        assertEquals("Sum is different than expected", new Double(123.0), transactionStatistics.getSum());
        assertEquals("Avg is different than expected", new Double(12.3), transactionStatistics.getAvg());
        assertEquals("Min is different than expected", new Double(12.3), transactionStatistics.getMin());
        assertEquals("Max is different than expected", new Double(12.3), transactionStatistics.getMax());
        assertEquals("Count is different than expected", new Long(10L), transactionStatistics.getCount());
    }
}

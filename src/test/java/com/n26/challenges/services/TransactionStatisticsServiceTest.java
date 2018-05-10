package com.n26.challenges.services;

import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.models.TransactionStatistics;
import com.n26.challenges.util.AppConfig;
import org.junit.Test;


import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionStatisticsServiceTest {


    private static final AppConfig APP_CONFIG = new AppConfig(10);

    private TransactionStatisticsService createStatisticsService(TransactionService transactionService) {
        return new TransactionStatisticsService(APP_CONFIG, transactionService);
    }

    private TransactionStatisticsService createStatisticsService(AppConfig appConfig, TransactionService transactionService) {
        return new TransactionStatisticsService(appConfig, transactionService);
    }

    @Test
    public void testSingleStatisticsResult() {
        TransactionService transactionService = new TransactionService(APP_CONFIG);
        TransactionStatisticsService transactionStatisticsService = createStatisticsService(transactionService);
        Double amount = 12.5;
        transactionService.addTransaction(new AddTransactionRequest(amount, System.currentTimeMillis()));
        transactionStatisticsService.updateStatistics();
        TransactionStatistics transactionStatistics = transactionStatisticsService.getStatistics();
        assertNotNull(transactionStatistics);
        assertEquals("Sum is different than expected", amount, transactionStatistics.getSum());
        assertEquals("Avg is different than expected", amount, transactionStatistics.getAverage());
        assertEquals("Min is different than expected", amount, transactionStatistics.getMin());
        assertEquals("Max is different than expected", amount, transactionStatistics.getMax());
        assertEquals("Count is different than expected", new Long(1L), transactionStatistics.getCount());
    }

    @Test
    public void testMultipleStatisticsResults() {
        TransactionService transactionService = new TransactionService(APP_CONFIG);
        TransactionStatisticsService transactionStatisticsService = createStatisticsService(transactionService);
        transactionService.addTransaction(new AddTransactionRequest(5.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(10.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(15.0, System.currentTimeMillis()));
        transactionStatisticsService.updateStatistics();
        TransactionStatistics transactionStatistics = transactionStatisticsService.getStatistics();
        assertNotNull(transactionStatistics);
        assertEquals("Sum is different than expected", new Double(30.0), transactionStatistics.getSum());
        assertEquals("Avg is different than expected", new Double(10.0), transactionStatistics.getAverage());
        assertEquals("Min is different than expected", new Double(5.0), transactionStatistics.getMin());
        assertEquals("Max is different than expected", new Double(15.0), transactionStatistics.getMax());
        assertEquals("Count is different than expected", new Long(3L), transactionStatistics.getCount());
    }

    @Test
    public void testEmptyStatisticsResults() {
        TransactionStatisticsService transactionStatisticsService = createStatisticsService(new TransactionService(APP_CONFIG));
        transactionStatisticsService.updateStatistics();
        TransactionStatistics transactionStatistics = transactionStatisticsService.getStatistics();
        assertNotNull(transactionStatistics);
        assertEquals("Sum is different than expected", new Double(0), transactionStatistics.getSum());
        assertEquals("Avg is different than expected", new Double(0.0), transactionStatistics.getAverage());
        assertEquals("Min is different than expected", new Double(0.0), transactionStatistics.getMin());
        assertEquals("Max is different than expected", new Double(0.0), transactionStatistics.getMax());
        assertEquals("Count is different than expected", new Long(0L), transactionStatistics.getCount());
    }

    @Test
    public void testRemoveStatisticsResults() throws InterruptedException {
        TransactionService transactionService = new TransactionService(APP_CONFIG);
        AppConfig appConfig = new AppConfig(5);
        TransactionStatisticsService transactionStatisticsService = createStatisticsService(appConfig, transactionService);
        transactionService.addTransaction(new AddTransactionRequest(5.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(10.0, System.currentTimeMillis()));
        transactionService.addTransaction(new AddTransactionRequest(15.0, Instant.now().minus(Duration.ofSeconds(appConfig.getMaxTransactionTimeInSeconds()-1)).toEpochMilli()));
        Thread.sleep(1010);
        transactionStatisticsService.updateStatistics();
        TransactionStatistics transactionStatistics = transactionStatisticsService.getStatistics();
        assertNotNull(transactionStatistics);
        assertEquals("Sum is different than expected", new Double(15.0), transactionStatistics.getSum());
        assertEquals("Avg is different than expected", new Double(7.5), transactionStatistics.getAverage());
        assertEquals("Min is different than expected", new Double(5.0), transactionStatistics.getMin());
        assertEquals("Max is different than expected", new Double(10.0), transactionStatistics.getMax());
        assertEquals("Count is different than expected", new Long(2L), transactionStatistics.getCount());
    }
}

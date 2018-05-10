package com.n26.challenges.services;

import com.n26.challenges.models.TransactionStatistics;
import com.n26.challenges.models.Transaction;
import com.n26.challenges.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>
 * Service handling the transaction statistics.
 * </p>
 */
@Service
public class TransactionStatisticsService {

    private static final int UPDATE_STATISTICS_DELAY = 500;

    private final TransactionService transactionService;
    private final AppConfig configuration;
    private TransactionStatistics latestProcessedTransactionStatistics = new TransactionStatistics();

    /**
     * Builds a new {@link TransactionStatisticsService}.
     *
     * @param configuration      the {@link AppConfig} to use
     * @param transactionService the {@link TransactionService} to use
     */
    @Autowired
    public TransactionStatisticsService(AppConfig configuration, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.configuration = configuration;
    }

    /**
     * Get the transaction statistics.
     *
     * @return {@link TransactionStatistics}
     */
    public TransactionStatistics getStatistics() {
        return latestProcessedTransactionStatistics;
    }

    /**
     * Periodically updates statistics.
     */
    @Scheduled(fixedDelay = UPDATE_STATISTICS_DELAY)
    public void updateStatistics() {
        Collection<Transaction> transactions = transactionService.getTransactions();
        Collection<Transaction> transactionsToRemove = new LinkedList<>();
        Instant sixtySecondsAgo = Instant.now().minus(Duration.ofSeconds(configuration.getMaxTransactionTimeInSeconds()));
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        BigDecimal sum = BigDecimal.ZERO;
        long count = 0L;
        synchronized (transactions) {
            for (Transaction transaction : transactions) {
                if (transaction.getTimestamp().isBefore(sixtySecondsAgo)) {
                    transactionsToRemove.add(transaction);
                } else {
                    count++;
                    sum = new BigDecimal(transaction.getAmount()).add(sum);
                    if (transaction.getAmount() > max) {
                        max = transaction.getAmount();
                    }

                    if (transaction.getAmount() < min) {
                        min = transaction.getAmount();
                    }
                }
            }
        }
        if (count > 0) {
            //Decimal 64 since there is a double limitation so no need for 128
            BigDecimal average = sum.divide(new BigDecimal(count), MathContext.DECIMAL64);
            latestProcessedTransactionStatistics = new TransactionStatistics(count, sum.doubleValue(), average.doubleValue(), max, min);
        } else {
            latestProcessedTransactionStatistics = new TransactionStatistics();
        }
        if (!transactionsToRemove.isEmpty()) {
            transactionService.updateTransactions(transactionsToRemove);
        }
    }
}

package com.n26.challenges.services;

import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.dtos.AddTransactionResponse;
import com.n26.challenges.models.Transaction;
import com.n26.challenges.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Service for handling transaction entities.
 * </p>
 */
@Service
public class TransactionService {
    private static final int HTTP_STATUS_OLD_TIMESTAMP = 204;
    private static final int HTTP_STATUS_CREATED = 201;

    private final AppConfig appConfig;
    private final List<Transaction> transactions = Collections.synchronizedList(new LinkedList<>());

    /**
     * Builds a new {@link TransactionService} instance.
     *
     * @param appConfig {@link AppConfig} to use
     */
    @Autowired
    public TransactionService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Adds a transaction if it meets certain criteria.
     *
     * @param request the {@link AddTransactionRequest} to use
     * @return {@link AddTransactionResponse}
     */
    public AddTransactionResponse addTransaction(@RequestBody AddTransactionRequest request) {
        if (isOldTimestamp(request.getTimestamp())) {
            return new AddTransactionResponse(HTTP_STATUS_OLD_TIMESTAMP);
        }

        Instant instant = Instant.ofEpochMilli(request.getTimestamp()).atZone(ZoneOffset.UTC).toInstant();

        Transaction transaction = new Transaction(request.getAmount(), instant);
        transactions.add(transaction);
        return new AddTransactionResponse(HTTP_STATUS_CREATED);
    }

    /**
     * Checks if the timestamp is too old to save.
     *
     * @param timestamp timestamp to check
     *
     * @return true if the timestamp is too old to save, false otherwise
     */
    private boolean isOldTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        Instant sixtySecondsAgo = Instant.now().minus(Duration.ofSeconds(appConfig.getMaxTransactionTimeInSeconds()));
        return instant.isBefore(sixtySecondsAgo);
    }

    /**
     * Returns the transactions.
     *
     * @return list of transactions
     */
    public Collection<Transaction> getTransactions() {
        return Collections.unmodifiableCollection(transactions);
    }

    /**
     * Updates the transactions by removing the provided list of transactions.
     *
     * @param transactionsToRemove transactions to remove
     */
    public void updateTransactions(Collection<Transaction> transactionsToRemove) {
        this.transactions.removeAll(transactionsToRemove);
    }
}

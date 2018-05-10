package com.n26.challenges.models;

import java.time.Instant;

/**
 * <p>
 * Transaction entity.
 * </p>
 */
public class Transaction {

    private Double amount;
    private Instant timestamp;

    /**
     * Builds a new {@link Transaction} instance.
     *
     * @param amount    amount of the transaction
     * @param timestamp timestamp of the transaction
     */
    public Transaction(Double amount, Instant timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return amount of the transaction
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Returns the timestamp of the transaction.
     *
     * @return timestamp of the transaction
     */
    public Instant getTimestamp() {
        return timestamp;
    }
}

package com.n26.challenges.dtos;

/**
 * <p>
 * Request a transaction addition.
 * </p>
 */
public class AddTransactionRequest {

    private double amount;
    private long timestamp;

    /**
     * Creates a new {@link AddTransactionRequest} instance.
     *
     * @param amount    amount of the transaction
     * @param timestamp timestamp of the transaction
     */
    public AddTransactionRequest(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * Required constructor for a spring controller's request body object.
     */
    public AddTransactionRequest() {
    }

    /**
     * Returns the amount.
     *
     * @return amount of the transaction
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Returns the timestamp.
     *
     * @return timestamp of the transaction
     */
    public long getTimestamp() {
        return timestamp;
    }
}

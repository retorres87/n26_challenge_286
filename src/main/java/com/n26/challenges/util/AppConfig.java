package com.n26.challenges.util;

/**
 * Centralizes the application configuration.
 */
public class AppConfig {
    private int maxTransactionTimeInSeconds;

    /**
     * Builds a new {@link AppConfig} instance.
     *
     * @param maxTransactionTimeInSeconds max transaction time in seconds
     */
    public AppConfig(int maxTransactionTimeInSeconds) {
        this.maxTransactionTimeInSeconds = maxTransactionTimeInSeconds;
    }

    /**
     * Returns the max transaction time.
     *
     * @return max transacttion time in seconds
     */
    public int getMaxTransactionTimeInSeconds() {
        return maxTransactionTimeInSeconds;
    }
}

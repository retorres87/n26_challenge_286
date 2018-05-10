package com.n26.challenges.models;

/**
 * <p>
 * Transaction statistics.
 * </p>
 */
public class TransactionStatistics {

    private Long count = 0L;

    private Double sum = 0.0;

    private Double avg = 0.0;

    private Double max = 0.0;

    private Double min = 0.0;

    /**
     * Builds a new {@link TransactionStatistics} instance.
     *
     * @param count   count of transactions
     * @param sum     sum of transactions
     * @param avg average of transactions
     * @param max     max of transactions
     * @param min     min of transactions
     */
    public TransactionStatistics(Long count, Double sum, Double avg, Double max, Double min) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    /**
     * Builds a new {@link TransactionStatistics} instance.
     */
    public TransactionStatistics() {
    }

    /**
     * Returns the total amount of transactions.
     *
     * @return total amount of transactions based on the statistics
     */
    public Long getCount() {
        return count;
    }

    /**
     * Returns the sum of the transactions amount.
     *
     * @return sum of the transactions based on the statistics
     */
    public Double getSum() {
        return sum;
    }

    /**
     * Returns the average of the transactions amount.
     *
     * @return average of the transactions amount based on the statistics
     */
    public Double getAvg() {
        return avg;
    }

    /**
     * Returns the max amount of the transactions list.
     *
     * @return max amount of the transactions list based on the statistics
     */
    public Double getMax() {
        return max;
    }

    /**
     * Returns the min amount of the transactions list.
     *
     * @return min amount of the transactions list based on the statistics
     */
    public Double getMin() {
        return min;
    }
}

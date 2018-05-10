package com.n26.challenges.dtos;

/**
 * <p>
 * Response to the add transacion.
 * </p>
 */
public class AddTransactionResponse {

    private final int status;

    /**
     * Builds a new {@link AddTransactionResponse} instance.
     *
     * @param status http status of the response
     */
    public AddTransactionResponse(int status) {
        this.status = status;
    }

    /**
     * Returns the http status.
     *
     * @return http status code
     */
    public int getStatus() {
        return status;
    }
}

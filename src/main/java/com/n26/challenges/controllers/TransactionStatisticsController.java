package com.n26.challenges.controllers;

import com.n26.challenges.models.TransactionStatistics;
import com.n26.challenges.services.TransactionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * Transaction statistics endpoint.
 * </p>
 */
@RestController
public class TransactionStatisticsController {

    private final TransactionStatisticsService transactionStatisticsService;

    /**
     * Creates a new {@link TransactionStatisticsController} instance.
     *
     * @param transactionStatisticsService the {@link TransactionStatisticsService} to use
     */
    @Autowired
    public TransactionStatisticsController(TransactionStatisticsService transactionStatisticsService) {
        this.transactionStatisticsService = transactionStatisticsService;
    }

    /**
     * Return the current transaction statisics.
     *
     * @return current transaction statistics
     */
    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionStatistics getStatistics() {
        return transactionStatisticsService.getStatistics();
    }
}

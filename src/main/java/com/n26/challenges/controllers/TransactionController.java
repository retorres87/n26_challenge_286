package com.n26.challenges.controllers;

import com.n26.challenges.dtos.AddTransactionRequest;
import com.n26.challenges.dtos.AddTransactionResponse;
import com.n26.challenges.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Transaction addition endpoint.
 * </p>
 */
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Builds a new {@link TransactionController} instance.
     *
     * @param transactionService the {@link TransactionService} to use.
     */
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Adds a transaction.
     *
     * @param request the {@link AddTransactionRequest} to use
     *
     * @return Response entity based on the service rules
     */
    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addTransaction(@RequestBody AddTransactionRequest request) {
        AddTransactionResponse response = transactionService.addTransaction(request);
        return ResponseEntity.status(response.getStatus()).build();
    }
}

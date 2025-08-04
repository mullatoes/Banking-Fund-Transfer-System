package com.kyeiiih.minibankingwithrecords.dto;

import java.math.BigDecimal;

public record TransferRequest(Long fromAccountId, Long toAccountId, BigDecimal amount) {
    public TransferRequest {
        if (fromAccountId == null || toAccountId == null || amount == null) {
            throw new IllegalArgumentException("All fields are required.");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
    }
}

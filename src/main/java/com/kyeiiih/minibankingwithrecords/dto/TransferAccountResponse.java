package com.kyeiiih.minibankingwithrecords.dto;

import java.math.BigDecimal;

public record TransferAccountResponse(
    Long fromAccountId,
    Long toAccountId,
    String fromAccountName,
    String toAccountName,
    BigDecimal amount) {
}

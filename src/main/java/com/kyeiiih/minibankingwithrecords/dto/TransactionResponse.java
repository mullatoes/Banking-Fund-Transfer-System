package com.kyeiiih.minibankingwithrecords.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(String type, BigDecimal amount, LocalDateTime timestamp) {
}

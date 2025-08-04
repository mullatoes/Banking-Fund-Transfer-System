package com.kyeiiih.minibankingwithrecords.dto;

import java.math.BigDecimal;

public record AccountResponse(Long accountId, String name, BigDecimal balance) {
}

package com.kyeiiih.minibankingwithrecords.dto;

import java.math.BigDecimal;

public record CreateAccountRequest(String name, BigDecimal initialDeposit) {

}

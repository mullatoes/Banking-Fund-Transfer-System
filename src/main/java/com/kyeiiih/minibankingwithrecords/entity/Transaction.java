package com.kyeiiih.minibankingwithrecords.entity;

import com.kyeiiih.minibankingwithrecords.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime timestamp;
}

package com.kyeiiih.minibankingwithrecords.service;

import com.kyeiiih.minibankingwithrecords.dto.CreateAccountRequest;
import com.kyeiiih.minibankingwithrecords.entity.Account;
import com.kyeiiih.minibankingwithrecords.repository.AccountRepository;
import com.kyeiiih.minibankingwithrecords.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private AccountRepository accountRepo;
    private TransactionRepository transactionRepo;
    private AccountService service;

    @BeforeEach
    void setUp() {
        accountRepo = org.mockito.Mockito.mock(AccountRepository.class);
        transactionRepo = org.mockito.Mockito.mock(TransactionRepository.class);
        service = new AccountService(accountRepo, transactionRepo);
    }

    @Test
    void testCreateAccount_success() {
        CreateAccountRequest request = new CreateAccountRequest("Alice", new BigDecimal("1000"));
        when(accountRepo.save(any())).thenAnswer(inv -> {
            Account a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        var response = service.createAccount(request);

        assertEquals("Alice", response.name());
        assertEquals(new BigDecimal("1000"), response.balance());

        verify(transactionRepo).save(any());
    }

    @Test
    void testCreateAccount_negativeDeposit_shouldThrow() {
        CreateAccountRequest request = new CreateAccountRequest("Bob", new BigDecimal("-1"));
        assertThrows(IllegalArgumentException.class, () -> service.createAccount(request));
    }
}
package com.kyeiiih.minibankingwithrecords.controller;

import com.kyeiiih.minibankingwithrecords.dto.*;
import com.kyeiiih.minibankingwithrecords.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(service.createAccount(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }


    @PostMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@RequestBody DepositRequest request) {
        return ResponseEntity.ok(service.deposit(request));
    }


    @PostMapping("/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(service.withdraw(request));
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTransactions(id));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferAccountResponse> transfer(@RequestBody TransferRequest request) {

        return ResponseEntity.ok(service.transferFunds(request));
    }

}

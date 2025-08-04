package com.kyeiiih.minibankingwithrecords.service;

import com.kyeiiih.minibankingwithrecords.dto.*;
import com.kyeiiih.minibankingwithrecords.entity.Account;
import com.kyeiiih.minibankingwithrecords.entity.Transaction;
import com.kyeiiih.minibankingwithrecords.enums.TransactionType;
import com.kyeiiih.minibankingwithrecords.repository.AccountRepository;
import com.kyeiiih.minibankingwithrecords.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {

        if (request.initialDeposit().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }

        Account account = new Account();
        account.setName(request.name());
        account.setBalance(request.initialDeposit());

        Account saved = accountRepo.save(account);

        saveTransaction(saved.getId(), request.initialDeposit(),true);

        return new AccountResponse(saved.getId(), saved.getName(), saved.getBalance());
    }

    @Transactional
    public AccountResponse deposit(DepositRequest request) {
        Account account = getAccountOrThrow(request.accountId());

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        account.setBalance(account.getBalance().add(request.amount()));
        accountRepo.save(account);

        saveTransaction(account.getId(),request.amount(),true);

        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    @Transactional
    public AccountResponse withdraw(WithdrawRequest request) {
        Account account = getAccountOrThrow(request.accountId());

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (account.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.amount()));
        accountRepo.save(account);

        saveTransaction(account.getId(), request.amount(),false);

        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    @Transactional
    public TransferAccountResponse transferFunds(TransferRequest request) {
        Account from = accountRepo.findByIdForUpdate(request.fromAccountId());

        Account to = accountRepo.findByIdForUpdate(request.toAccountId());

        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid account IDs");
        }

        if(request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (from.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in sender's account");
        }

        //deduct from sender
        from.setBalance(from.getBalance().subtract(request.amount()));
        accountRepo.save(from);
        saveTransaction(from.getId(), request.amount().negate(),false);

        //add to receiver
        to.setBalance(to.getBalance().add(request.amount()));
        accountRepo.save(to);
        saveTransaction(to.getId(), request.amount(),true);

        return new TransferAccountResponse(
                from.getId(),
                to.getId(),
                from.getName(),
                to.getName(),
                request.amount()
        );

    }

    public AccountResponse getAccount(Long id) {
        Account account = getAccountOrThrow(id);
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    public List<TransactionResponse> getTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepo.findByAccountIdOrderByTimestampDesc(accountId);
        return transactions.stream()
                .map(tx -> new TransactionResponse(tx.getType().name(), tx.getAmount(), tx.getTimestamp()))
                .collect(Collectors.toList());
    }

    private Account getAccountOrThrow(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    private void saveTransaction(Long accountId, BigDecimal amount, boolean isDeposit) {
        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        if (isDeposit)
            tx.setType(TransactionType.DEPOSIT);
        else
            tx.setType(TransactionType.WITHDRAW);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepo.save(tx);
    }
}

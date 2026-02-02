package com.fintech.service;

import com.fintech.dto.TransactionDTO;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com ID: " + id));
        return toDTO(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> findByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + accountId));
        
        return transactionRepository.findByAccount(account).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO create(TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + transactionDTO.getAccountId()));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setDescription(transactionDTO.getDescription());

        // Atualizar saldo da conta baseado no tipo de transação
        BigDecimal currentBalance = account.getBalance();
        BigDecimal transactionAmount = transaction.getAmount();

        switch (transaction.getTransactionType()) {
            case DEPOSIT:
                account.setBalance(currentBalance.add(transactionAmount));
                break;
            case WITHDRAWAL:
            case PAYMENT:
                if (currentBalance.compareTo(transactionAmount) < 0) {
                    throw new RuntimeException("Saldo insuficiente. Saldo atual: " + currentBalance);
                }
                account.setBalance(currentBalance.subtract(transactionAmount));
                break;
            case TRANSFER:
                if (currentBalance.compareTo(transactionAmount) < 0) {
                    throw new RuntimeException("Saldo insuficiente para transferência. Saldo atual: " + currentBalance);
                }
                account.setBalance(currentBalance.subtract(transactionAmount));
                break;
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return toDTO(savedTransaction);
    }

    @Transactional
    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada com ID: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getDescription(),
                transaction.getTransactionDate()
        );
    }
}

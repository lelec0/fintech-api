package com.fintech.config;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.User;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import com.fintech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Dados já existem no banco. Pulando inicialização.");
            return;
        }

        log.info("Inicializando dados fake...");

        // Criar usuários
        User user1 = new User();
        user1.setName("João Silva");
        user1.setEmail("joao.silva@email.com");
        user1.setCpf("12345678901");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("Maria Santos");
        user2.setEmail("maria.santos@email.com");
        user2.setCpf("98765432100");
        user2 = userRepository.save(user2);

        log.info("Usuários criados: {} e {}", user1.getName(), user2.getName());

        // Criar contas
        Account account1 = new Account();
        account1.setUser(user1);
        account1.setBalance(new BigDecimal("5000.00"));
        account1.setAccountType(Account.AccountType.CHECKING);
        account1 = accountRepository.save(account1);

        Account account2 = new Account();
        account2.setUser(user1);
        account2.setBalance(new BigDecimal("10000.00"));
        account2.setAccountType(Account.AccountType.SAVINGS);
        account2 = accountRepository.save(account2);

        Account account3 = new Account();
        account3.setUser(user2);
        account3.setBalance(new BigDecimal("3000.00"));
        account3.setAccountType(Account.AccountType.CHECKING);
        account3 = accountRepository.save(account3);

        log.info("Contas criadas para os usuários");

        // Criar transações
        Transaction transaction1 = new Transaction();
        transaction1.setAccount(account1);
        transaction1.setAmount(new BigDecimal("1000.00"));
        transaction1.setTransactionType(Transaction.TransactionType.DEPOSIT);
        transaction1.setDescription("Depósito inicial");
        transaction1.setTransactionDate(LocalDateTime.now().minusDays(5));
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAccount(account1);
        transaction2.setAmount(new BigDecimal("500.00"));
        transaction2.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        transaction2.setDescription("Saque em caixa eletrônico");
        transaction2.setTransactionDate(LocalDateTime.now().minusDays(2));
        transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAccount(account2);
        transaction3.setAmount(new BigDecimal("2000.00"));
        transaction3.setTransactionType(Transaction.TransactionType.DEPOSIT);
        transaction3.setDescription("Transferência recebida");
        transaction3.setTransactionDate(LocalDateTime.now().minusDays(1));
        transactionRepository.save(transaction3);

        log.info("Transações criadas");
        log.info("✅ Dados fake inicializados com sucesso!");
    }
}

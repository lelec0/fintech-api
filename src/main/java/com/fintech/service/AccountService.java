package com.fintech.service;

import com.fintech.dto.AccountDTO;
import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));
        return toDTO(account);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
        
        return accountRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO create(AccountDTO accountDTO) {
        User user = userRepository.findById(accountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + accountDTO.getUserId()));

        Account account = new Account();
        account.setUser(user);
        account.setBalance(accountDTO.getBalance() != null ? accountDTO.getBalance() : BigDecimal.ZERO);
        account.setAccountType(accountDTO.getAccountType() != null ? 
                accountDTO.getAccountType() : Account.AccountType.CHECKING);

        Account savedAccount = accountRepository.save(account);
        return toDTO(savedAccount);
    }

    @Transactional
    public AccountDTO update(Long id, AccountDTO accountDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));

        if (accountDTO.getAccountType() != null) {
            account.setAccountType(accountDTO.getAccountType());
        }

        Account updatedAccount = accountRepository.save(account);
        return toDTO(updatedAccount);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Conta não encontrada com ID: " + id);
        }
        accountRepository.deleteById(id);
    }

    private AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getUser().getId(),
                account.getBalance(),
                account.getAccountType(),
                account.getCreatedAt()
        );
    }
}

package com.fintech.dto;

import com.fintech.model.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
    private BigDecimal balance;
    private Account.AccountType accountType;
    private LocalDateTime createdAt;
}

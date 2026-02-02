package com.fintech.dto;

import com.fintech.model.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    
    @NotNull(message = "ID da conta é obrigatório")
    private Long accountId;
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;
    
    @NotNull(message = "Tipo de transação é obrigatório")
    private Transaction.TransactionType transactionType;
    
    private String description;
    private LocalDateTime transactionDate;
}

package com.fintech.controller;

import com.fintech.dto.TransactionDTO;
import com.fintech.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testFluxoCompletoTransacao() throws Exception {
        // 1. Criar depósito (usa accountId = 1 dos dados fake)
        TransactionDTO novaTransacao = new TransactionDTO();
        novaTransacao.setAccountId(1L);
        novaTransacao.setAmount(new BigDecimal("500.00"));
        novaTransacao.setTransactionType(Transaction.TransactionType.DEPOSIT);
        novaTransacao.setDescription("Depósito de teste");

        String transacaoJson = objectMapper.writeValueAsString(novaTransacao);

        String response = mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").value(500.00))
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        TransactionDTO transacaoCriada = objectMapper.readValue(response, TransactionDTO.class);
        Long transactionId = transacaoCriada.getId();

        // 2. Buscar transação criada
        mockMvc.perform(get("/api/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionId))
                .andExpect(jsonPath("$.amount").value(500.00));

        // 3. Listar todas as transações
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").exists());

        // 4. Listar transações de uma conta (extrato)
        mockMvc.perform(get("/api/transactions/account/{accountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 5. Criar saque
        TransactionDTO saque = new TransactionDTO();
        saque.setAccountId(1L);
        saque.setAmount(new BigDecimal("100.00"));
        saque.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        saque.setDescription("Saque de teste");
        String saqueJson = objectMapper.writeValueAsString(saque);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saqueJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionType").value("WITHDRAWAL"));

        // 6. Deletar transação
        mockMvc.perform(delete("/api/transactions/{id}", transactionId))
                .andExpect(status().isNoContent());
    }
}

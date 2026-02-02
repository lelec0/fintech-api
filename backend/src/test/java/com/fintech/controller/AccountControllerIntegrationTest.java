package com.fintech.controller;

import com.fintech.dto.AccountDTO;
import com.fintech.model.Account;
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
class AccountControllerIntegrationTest {

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
    void testFluxoCompletoConta() throws Exception {
        // 1. Criar conta (usa userId = 1 dos dados fake)
        AccountDTO novaConta = new AccountDTO();
        novaConta.setUserId(1L);
        novaConta.setBalance(new BigDecimal("2000.00"));
        novaConta.setAccountType(Account.AccountType.CHECKING);

        String contaJson = objectMapper.writeValueAsString(novaConta);

        String response = mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.accountNumber").exists())
                .andExpect(jsonPath("$.balance").value(2000.00))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AccountDTO contaCriada = objectMapper.readValue(response, AccountDTO.class);
        Long accountId = contaCriada.getId();

        // 2. Buscar conta criada
        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.balance").value(2000.00));

        // 3. Listar todas as contas
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").exists());

        // 4. Listar contas de um usuário
        mockMvc.perform(get("/api/accounts/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 5. Atualizar conta
        novaConta.setAccountType(Account.AccountType.SAVINGS);
        String contaAtualizadaJson = objectMapper.writeValueAsString(novaConta);

        mockMvc.perform(put("/api/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contaAtualizadaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountType").value("SAVINGS"));

        // 6. Deletar conta
        mockMvc.perform(delete("/api/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }
}

package com.fintech.controller;

import com.fintech.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class UserControllerIntegrationTest {

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
    void testFluxoCompletoUsuario() throws Exception {
        // 1. Criar usuário
        UserDTO novoUsuario = new UserDTO();
        novoUsuario.setName("Teste Usuario");
        novoUsuario.setEmail("teste.usuario@email.com");
        novoUsuario.setCpf("11122233344");

        String usuarioJson = objectMapper.writeValueAsString(novoUsuario);

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Teste Usuario"))
                .andExpect(jsonPath("$.email").value("teste.usuario@email.com"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO usuarioCriado = objectMapper.readValue(response, UserDTO.class);
        Long userId = usuarioCriado.getId();

        // 2. Buscar usuário criado
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Teste Usuario"));

        // 3. Listar todos os usuários
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").exists());

        // 4. Atualizar usuário
        novoUsuario.setName("Teste Usuario Atualizado");
        String usuarioAtualizadoJson = objectMapper.writeValueAsString(novoUsuario);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Teste Usuario Atualizado"));

        // 5. Deletar usuário
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }
}

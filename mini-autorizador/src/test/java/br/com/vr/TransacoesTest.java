package br.com.vr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.application.domain.dto.TransacaoDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacoesTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void realizarTransacaoCartaoInexistenteTest() throws Exception {
        TransacaoDTO transacao = new TransacaoDTO("1234567890123458", "121315", BigDecimal.valueOf(10.00));
        mockMvc.perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("CARTAO_INEXISTENTE"));;
    }
    
    @Test
    void realizarTransacaoCartaoSenhaIncorretaTest() throws Exception {
        TransacaoDTO transacao = new TransacaoDTO("1234567890123457", "121314", BigDecimal.valueOf(10.00));
        mockMvc.perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SENHA_INVALIDA"));;
    }
    
    @Test
    void realizarTransacaoCartaoSaldoInsuficienteTest() throws Exception {
        TransacaoDTO transacao = new TransacaoDTO("1234567890123457", "121315", BigDecimal.valueOf(1000.00));
        mockMvc.perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SALDO_INSUFICIENTE"));;
    }
    
    @Test
    void realizarTransacaoCartaoTest() throws Exception {
        TransacaoDTO transacao = new TransacaoDTO("1234567890123457", "121315", BigDecimal.valueOf(10.00));
        mockMvc.perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));;
    }
    
}


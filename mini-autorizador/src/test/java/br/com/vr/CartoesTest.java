package br.com.vr;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.application.domain.dto.CartaoDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class CartoesTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarNovoCartaoTest() throws Exception {
        CartaoDTO cartao = new CartaoDTO("1234567890123456", "121314", new BigDecimal(500.00));
        mockMvc.perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao", is("1234567890123456")))
                .andExpect(jsonPath("$.senha", is("121314")))
                .andExpect(jsonPath("$.saldo", is(500.00)));
    }

    @Test
    void verificarSaldoCartaoRecemCriadoTest() throws Exception {
    	
        CartaoDTO cartao = new CartaoDTO("1234567890123457", "121315", null);
        
        mockMvc.perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartao)))
        		.andExpect(status().isCreated());

        mockMvc.perform(get("/cartoes/" + cartao.getNumeroCartao()).contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("500.00"));
        
    }

}


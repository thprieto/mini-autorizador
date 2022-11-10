package br.com.vr.application.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.istack.NotNull;

import br.com.vr.application.domain.dto.CartaoDTO;
import br.com.vr.application.domain.dto.StatusDTO;
import br.com.vr.application.domain.dto.TransacaoDTO;
import br.com.vr.application.service.CartaoService;
import lombok.Data;

@Data
@RestController
@RequestMapping
public class MiniAutorizadorController {

	@Autowired
    private CartaoService cartaoService;
    
    @PostMapping("/cartoes")
    public ResponseEntity<CartaoDTO> criarNovoCartao(@RequestBody CartaoDTO cartaoDTO) {
    	
    	CartaoDTO cartao = cartaoService.consultarCartao(cartaoDTO.getNumeroCartao());
    	
    	if (cartao != null) {
    		return new ResponseEntity<>(cartao, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	
        cartao = cartaoService.criarCartao(cartaoDTO);
        return new ResponseEntity<>(cartao, HttpStatus.CREATED);
        
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldoCartao(@PathVariable @NotNull String numeroCartao) {
    	
    	CartaoDTO cartao = cartaoService.consultarCartao(numeroCartao);
    	
    	if (cartao == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	
        return new ResponseEntity<>(cartao.getSaldo(), HttpStatus.OK);
        
    }
    
    @PostMapping("/transacoes")
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
    	
    	StatusDTO statusDTO = cartaoService.subtrairSaldo(transacaoDTO);
    	
        return new ResponseEntity<>(statusDTO.getMensagem(), statusDTO.getHttpStatus());
        
    }

}

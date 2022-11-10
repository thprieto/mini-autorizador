package br.com.vr.application.service;

import org.springframework.stereotype.Service;

import br.com.vr.application.domain.dto.CartaoDTO;
import br.com.vr.application.domain.dto.StatusDTO;
import br.com.vr.application.domain.dto.TransacaoDTO;

@Service
public interface CartaoService {

    CartaoDTO criarCartao(CartaoDTO cartaoDTO);

    CartaoDTO consultarCartao(String numeroCartao);

	StatusDTO subtrairSaldo(TransacaoDTO transacaoDTO);
}

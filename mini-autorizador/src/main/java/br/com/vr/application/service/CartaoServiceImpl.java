package br.com.vr.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vr.application.domain.dto.CartaoDTO;
import br.com.vr.application.domain.dto.StatusDTO;
import br.com.vr.application.domain.dto.TransacaoDTO;
import br.com.vr.application.domain.dto.ValidacaoDTO;
import br.com.vr.application.domain.entity.Cartao;
import br.com.vr.application.domain.repository.CartaoRepository;
import br.com.vr.application.util.Constants;

@Service
public class CartaoServiceImpl implements CartaoService {
	
    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    @Transactional
    public CartaoDTO criarCartao(CartaoDTO cartaoDTO) {
    	
        Cartao cartao = new Cartao(cartaoDTO.getNumeroCartao(), cartaoDTO.getSenha(), Constants.SALDO_INICIAL);
        cartaoRepository.save(cartao);
        
        return new CartaoDTO(cartao);
    }

    @Override
    public CartaoDTO consultarCartao(String numeroCartao) {
        Cartao cartao = cartaoRepository.findById(numeroCartao).orElse(null);
        return cartao != null ? new CartaoDTO(cartao) : null;
    }

	@Override
	@Transactional
	public StatusDTO subtrairSaldo(TransacaoDTO transacaoDTO) {

		ValidacaoDTO validacaoDTO = validarTransacao( transacaoDTO );
		
		if ( Constants.HTTP_STATUS_OK.equals( validacaoDTO.getStatus().getMensagem() ) ) {
			
			Cartao cartao = new Cartao( validacaoDTO.getCartao() );
			cartao.setSaldo(cartao.getSaldo().subtract(transacaoDTO.getValorTransacao()));
			cartaoRepository.save(cartao);
			
		}
		
		return validacaoDTO.getStatus();
	}

	private ValidacaoDTO validarTransacao(TransacaoDTO transacaoDTO) {
		
		StatusDTO statusDTO;
		CartaoDTO cartaoDTO = null;
		Optional<Cartao> cartao = cartaoRepository.findById(transacaoDTO.getNumeroCartao());
		
		if (cartao.isPresent()) {
			cartaoDTO = new CartaoDTO(cartao.get());
		}
		
		if ( cartaoDTO == null ) {
			
			statusDTO = new StatusDTO(Constants.CARTAO_INEXISTENTE, HttpStatus.UNPROCESSABLE_ENTITY);
			 
		} else if ( !transacaoDTO.getSenhaCartao().equals(cartaoDTO.getSenha()) ) {
			
			statusDTO = new StatusDTO(Constants.SENHA_INVALIDA, HttpStatus.UNPROCESSABLE_ENTITY);
			
		} else if ( transacaoDTO.getValorTransacao().doubleValue() > cartaoDTO.getSaldo().doubleValue() ) {
			
			statusDTO = new StatusDTO(Constants.SALDO_INSUFICIENTE, HttpStatus.UNPROCESSABLE_ENTITY);
			
		} else {
			
			statusDTO = new StatusDTO(Constants.HTTP_STATUS_OK, HttpStatus.CREATED);
			
		}
		
		return new ValidacaoDTO(cartaoDTO, statusDTO);
	}
}

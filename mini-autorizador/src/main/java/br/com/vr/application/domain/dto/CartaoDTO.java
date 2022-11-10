package br.com.vr.application.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.vr.application.domain.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numeroCartao;
    private String senha;
    private BigDecimal saldo;

    public CartaoDTO(Cartao cartao) {
        setNumeroCartao(cartao.getNumeroCartao());
        setSenha(cartao.getSenha());
        setSaldo(cartao.getSaldo());
    }
}
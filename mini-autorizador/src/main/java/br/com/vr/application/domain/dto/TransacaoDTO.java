package br.com.vr.application.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valorTransacao;

}

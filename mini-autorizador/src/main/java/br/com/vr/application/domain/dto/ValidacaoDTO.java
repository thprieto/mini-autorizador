package br.com.vr.application.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CartaoDTO cartao;
    private StatusDTO status;

}
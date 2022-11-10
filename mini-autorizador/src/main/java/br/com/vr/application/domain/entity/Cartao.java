package br.com.vr.application.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.vr.application.domain.dto.CartaoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "miniautorizador", name = "cartao")
public class Cartao {
	
    @Id
    @Column(name = "numero_cartao", nullable = false)
    private String numeroCartao;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;
    
	public Cartao(String numeroCartao, String senha, BigDecimal saldo) {
		this.numeroCartao = numeroCartao;
		this.senha = senha;
		this.saldo = saldo;
	}  
	
	public Cartao(CartaoDTO cartaoDTO) {
		this.numeroCartao = cartaoDTO.getNumeroCartao();
		this.senha = cartaoDTO.getSenha();
		this.saldo = cartaoDTO.getSaldo();
	}

}

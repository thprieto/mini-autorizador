package br.com.vr.application.domain.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mensagem;
    private HttpStatus httpStatus;
    
}
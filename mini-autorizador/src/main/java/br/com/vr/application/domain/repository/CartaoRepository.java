package br.com.vr.application.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.application.domain.entity.Cartao;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, String> {

}

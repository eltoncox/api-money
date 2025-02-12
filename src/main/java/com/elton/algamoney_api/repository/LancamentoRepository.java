package com.elton.algamoney_api.repository;

import com.elton.algamoney_api.model.Lancamento;
import com.elton.algamoney_api.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
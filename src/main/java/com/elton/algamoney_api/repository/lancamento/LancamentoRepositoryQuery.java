package com.elton.algamoney_api.repository.lancamento;

import com.elton.algamoney_api.model.Lancamento;
import com.elton.algamoney_api.repository.filter.LancamentoFilter;
import com.elton.algamoney_api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}

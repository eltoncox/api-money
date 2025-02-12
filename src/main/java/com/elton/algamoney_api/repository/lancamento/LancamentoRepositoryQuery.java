package com.elton.algamoney_api.repository.lancamento;


import com.elton.algamoney_api.model.Lancamento;
import com.elton.algamoney_api.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}

package model.dao;

import java.sql.Date;
import java.util.List;

import model.entities.Lancamento;

public interface LancamentoDao {
	
	void inserir(Lancamento lancamento);
	void atualizar(Lancamento lancamento);
	void excluirPeloId(Integer id);
	List<Lancamento> buscarTodos(Integer idClienteServico);
	List<Lancamento> verLancamentos(Date dataInicial, Date dataFinal, int idClienteServico);
	
}

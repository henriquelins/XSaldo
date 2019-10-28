package model.dao;

import java.util.List;

import model.entities.ClienteServico;

public interface ClienteServicoDao {
	
	void inserir(ClienteServico clienteServico);
	void atualizar(ClienteServico clienteServico);
	void excluirPeloId(Integer id);
	void atualizarSaldo(int saldoAtual, int idClienteServico);
	ClienteServico buscarPeloId(Integer id);
	ClienteServico buscarPeloIdCliente(Integer id_cliente_servico);
	List<ClienteServico> buscarTodos();
	List<ClienteServico> buscarPeloNomeFantasia(String nomeFantasia);
	List<ClienteServico> buscarServicosDoCliente(Integer idCliente);

}

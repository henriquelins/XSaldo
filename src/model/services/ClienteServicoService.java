package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.ClienteServicoDao;
import model.dao.DaoFactory;
import model.entities.ClienteServico;

public class ClienteServicoService {

	private ClienteServicoDao dao = DaoFactory.createClienteServicoDao();

	public List<ClienteServico> findAll() {

		return dao.buscarTodos();

	}

	public void clienteServicoNovoOuEditar(ClienteServico clienteServico) {

		if (clienteServico.getIdClienteServico() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",

					"Você deseja salvar um novo serviço?");

			if (result.get() == ButtonType.OK) {

				dao.inserir(clienteServico);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do serviço?");

			if (result.get() == ButtonType.OK) {

				dao.atualizar(clienteServico);

			}

		}

	}

	public void remove(ClienteServico clienteServico) {

		dao.excluirPeloId(clienteServico.getIdClienteServico());

	}

	public List<ClienteServico> buscarServicosDoCliente(Integer idCliente) {

		return dao.buscarServicosDoCliente(idCliente);

	}

	public ClienteServico buscarPeloIdCliente(Integer id_cliente_servico) {

		return dao.buscarPeloIdCliente(id_cliente_servico);

	}

}

package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

	private ClienteDao dao = DaoFactory.createClienteDao();

	public List<Cliente> findAll() {

		return dao.buscarTodos();

	}

	public void clienteNovoOuEditar(Cliente cliente) {

		if (cliente.getIdCliente() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar o novo cliente " + cliente.getNomeFantasia() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.inserir(cliente);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do cliente " + cliente.getNomeFantasia() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.atualizar(cliente);

			}

		}

	}

	public void remove(Cliente cliente) {

		dao.excluirPeloId(cliente.getIdCliente());

	}

}

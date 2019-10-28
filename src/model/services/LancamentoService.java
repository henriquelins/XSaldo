package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.LancamentoDao;
import model.entities.ClienteServico;
import model.entities.Lancamento;

public class LancamentoService {

	private LancamentoDao lancamentoDao = DaoFactory.createLancamentoDao();

	public void lancamentoSaidaOuEntrada(Lancamento lancamento, ClienteServico clienteServico) {

		// try {

		switch (lancamento.getTipoDoLancamento()) {

		case ("Entrada de créditos (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja dar entrada de créditos no serviço " + clienteServico.getNomeDoServico() + " ?");

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento);

			}

			break;

		case ("Saída de créditos (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
					"Você deseja dar saída de créditos no serviço " + clienteServico.getNomeDoServico() + " ?");

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento);

			}

			break;

		case ("Somatório de pedidos (++)"):

			Optional<ButtonType> result2 = Alerts.showConfirmation("Confirmação",
					"Você deseja adicionar no somatório de créditos no serviço " + clienteServico.getNomeDoServico()
							+ " ?");

			if (result2.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento);

			}

			break;

		}

	}

	public List<Lancamento> verLancamentos(Date DataInicial, Date DataFinal, int idClienteServico) {

		return lancamentoDao.verLancamentos(DataInicial, DataFinal, idClienteServico);

	}

}

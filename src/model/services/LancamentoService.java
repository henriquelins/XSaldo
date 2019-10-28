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

		case ("Entrada de cr�ditos (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja dar entrada de cr�ditos no servi�o " + clienteServico.getNomeDoServico() + " ?");

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento);

			}

			break;

		case ("Sa�da de cr�ditos (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja dar sa�da de cr�ditos no servi�o " + clienteServico.getNomeDoServico() + " ?");

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento);

			}

			break;

		case ("Somat�rio de pedidos (++)"):

			Optional<ButtonType> result2 = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja adicionar no somat�rio de cr�ditos no servi�o " + clienteServico.getNomeDoServico()
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

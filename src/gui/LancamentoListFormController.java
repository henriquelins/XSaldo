package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.ClienteServico;
import model.entities.Lancamento;
import model.entities.Usuario;
import model.services.LancamentoService;

public class LancamentoListFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private Cliente cliente;

	private ClienteServico clienteServico;

	private LancamentoService lancamentoService;

	private static ObservableList<Lancamento> listaVerLancamentos;

	// @FXML variáveis

	@FXML
	private Label labelTituloCliente;

	@FXML
	private Label labelNomeCliente;

	@FXML
	private Label labelNomeServico;

	@FXML
	private Label labelNomeProduto;

	@FXML
	private DatePicker datePickerDataInicial;

	@FXML
	private DatePicker datePickerDataFinal;

	@FXML
	private TableView<Lancamento> tableViewLancamento;

	@FXML
	private TableColumn<Lancamento, Integer> tableColumnId;

	@FXML
	private TableColumn<Lancamento, String> tableColumnDataLancamento;

	@FXML
	private TableColumn<Lancamento, Integer> tableColumnSaldoAnterior;

	@FXML
	private TableColumn<Lancamento, String> tableColumnOperador;

	@FXML
	private TableColumn<Lancamento, Integer> tableColumnQuantidade;

	@FXML
	private TableColumn<Lancamento, String> tableColumnIgualdade;

	@FXML
	private TableColumn<Lancamento, Integer> tableColumnSaldoAtual;

	@FXML
	private TableColumn<Lancamento, String> tableColumnDetalhamento;

	@FXML
	private Button buttonPesquisar;

	// @FXML event

	@FXML
	public void onButtonPesquisarAction(ActionEvent event) {

		if (datePickerDataInicial.getValue() == null || datePickerDataFinal.getValue() == null) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "Digite ou selecione a data", AlertType.ERROR);

		} else if (datePickerDataFinal.getValue().isBefore(datePickerDataInicial.getValue())) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "A data final não pode ser maior que a inicial",
					AlertType.ERROR);

		} else {

			listaVerLancamentos = FXCollections.observableArrayList(lancamentoService.verLancamentos(
					Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()),
					Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()),
					getClienteServico().getIdClienteServico()));

			if (listaVerLancamentos.isEmpty() == true) {

				Alerts.showAlert("Ver lançamento", "Lista vazia", null, AlertType.ERROR);

			} else {

				tableViewLancamento.setItems(listaVerLancamentos);

			}

		}

	}

	@Override
	public void onDataChanged() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	private void initializeNodes() {

		lancamentoService = new LancamentoService();

		labelTituloCliente.setText(new Strings().getTitleLancamentoList());

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idLancamento"));
		tableColumnDataLancamento.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.setDateFormat(param.getValue().getDataDoLancamento())));
		tableColumnSaldoAnterior.setCellValueFactory(new PropertyValueFactory<>("saldoAnterior"));
		tableColumnOperador.setCellValueFactory(new PropertyValueFactory<>("tipoDoLancamento"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeDoPedido"));
		tableColumnIgualdade.setCellValueFactory((param) -> new SimpleStringProperty("="));
		tableColumnSaldoAtual.setCellValueFactory(new PropertyValueFactory<>("saldoAtual"));
		tableColumnDetalhamento.setCellValueFactory(new PropertyValueFactory<>("observacoesLancamento"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLancamento.prefHeightProperty().bind(stage.heightProperty());

	}

	public void carregarCampos(Cliente cliente, ClienteServico clienteServico, Usuario usuario) {

		labelNomeCliente.setText(cliente.getNomeFantasia());
		labelNomeServico.setText(clienteServico.getNomeDoServico());
		labelNomeProduto.setText(clienteServico.getProdutoDoServico());

		setClienteServico(clienteServico);

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteServico getClienteServico() {
		return clienteServico;
	}

	public void setClienteServico(ClienteServico clienteServico) {
		this.clienteServico = clienteServico;
	}

	public LancamentoService getLancamentoService() {
		return lancamentoService;
	}

	public void setLancamentoService(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}

}

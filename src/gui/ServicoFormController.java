package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Strings;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.ClienteServico;
import model.entities.Usuario;
import model.services.ClienteServicoService;

public class ServicoFormController implements Initializable, DataChangeListener {

	// Java vari�veis

	private static Scene servicoFormScene;

	private Usuario usuario;

	private Cliente cliente;

	private ClienteServico clienteServico;

	private ClienteServico compararClienteServico;

	private ClienteServicoService service;

	private ClienteSelecionadoFormController clienteSelecionadoFormController;

	// Lista de ouvintes para receber alguma modifica��o

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// @FXML vari�veis

	@FXML
	private Label labelTituloTela;

	@FXML
	private Label labelNomeFantasia;

	@FXML
	private TextField textFieldNomeDoServico;

	@FXML
	private TextField textFieldCnpjParaCobranca;

	@FXML
	private ComboBox<String> comboBoxProduto;

	@FXML
	private TextField textFieldValorUnitario;

	@FXML
	private TextField textFieldLimiteMinimo;

	@FXML
	private TextArea textAreaObservacoesDoServico;

	@FXML
	private Button buttonSalvarServico;

	// @FXML event

	// Evento bot�o salvar ou editar servi�o

	@FXML
	public void onButtonSalvarServicoAction(ActionEvent event) {

		setClienteServico(getFormData(cliente));

		boolean ok = compararCampos();

		if (ok == false) {

			service.clienteServicoNovoOuEditar(clienteServico);
			Utils.fecharTelaServicoFormAction();
			notifyDataChangeListeners();

		} else {

			Alerts.showAlert("Cadastro de servi�os", "Editar servi�os", "N�o houve altera��o no registro",
					AlertType.INFORMATION);

		}

	}

	// Comparar todos os campos

	private boolean compararCampos() {

		boolean ok = false;

		if (clienteServico == null) {

			return ok;

		} else if (clienteServico.equals(compararClienteServico)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Lista do comboBox

	private List<String> listaProdutos() {

		List<String> lista = new ArrayList<>();
		lista.add("Crach�s em pvc");
		lista.add("Crach�s em pvc + acura");
		lista.add("Crach�s em pvc + mifare");
		lista.add("Crach�s em pvc adesivado");
		lista.add("Cart�o pvc personalizado");
		lista.add("Cart�o acura");
		lista.add("Cart�o mifare");
		lista.add("Credencial");
		lista.add("Botton");

		return lista;

	}

	// Adiciona a lista um ouvinte, quando h� uma modifica��o
	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Fun��o que faz a atualiza��o da tabela
	private void notifyDataChangeListeners() {

		for (DataChangeListener listener : dataChangeListeners) {

			listener.onDataChanged();

		}

	}

	// Listener

	@Override
	public void onDataChanged() {

		clienteSelecionadoFormController.updateDadosServicos();

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// M�todo com os objetos que devem ser inicializados

	private void initializeNodes() {

		clienteServico = new ClienteServico();
		service = new ClienteServicoService();

		comboBoxProduto.setItems(FXCollections.observableArrayList(listaProdutos()));

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, ClienteServico clienteServico, Usuario usuario) {

		if (clienteServico != null) {

			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia());
			textFieldNomeDoServico.setText(clienteServico.getNomeDoServico());
			textFieldCnpjParaCobranca.setText(clienteServico.getCnpjDoServico());
			comboBoxProduto.getSelectionModel().select(clienteServico.getProdutoDoServico());
			textFieldValorUnitario.setText(String.valueOf(clienteServico.getValorUnitario()));
			textFieldLimiteMinimo.setText(String.valueOf(clienteServico.getLimiteMinimo()));
			textAreaObservacoesDoServico.setText(String.valueOf(clienteServico.getObservacoesServico()));

		} else {

			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia());

		}

		setCliente(cliente);
		setClienteServico(clienteServico);
		setCompararClienteServico(clienteServico);
		setUsuario(usuario);

	}

	// Tratamento dos campos

	private ClienteServico getFormData(Cliente cliente) {

		ClienteServico clienteServico = new ClienteServico();

		if (textFieldNomeDoServico.getText() == null || textFieldNomeDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o nome do servi�o",
					AlertType.INFORMATION);

			textFieldNomeDoServico.requestFocus();

			clienteServico = null;

		} else if (textFieldCnpjParaCobranca.getText() == null
				|| textFieldCnpjParaCobranca.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o CNPJ do servi�o",
					AlertType.INFORMATION);

			textFieldCnpjParaCobranca.requestFocus();

			clienteServico = null;

		} else if (comboBoxProduto.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Selecione o produto", AlertType.INFORMATION);

			comboBoxProduto.requestFocus();

			clienteServico = null;

		} else if (textFieldValorUnitario.getText() == null || textFieldValorUnitario.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o valor unit�rio",
					AlertType.INFORMATION);

			textFieldValorUnitario.requestFocus();

			clienteServico = null;

		} else if (textFieldLimiteMinimo.getText() == null || textFieldLimiteMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o limite m�nimo",
					AlertType.INFORMATION);

			textFieldLimiteMinimo.requestFocus();

			clienteServico = null;

		} else if (textAreaObservacoesDoServico.getText() == null
				|| textAreaObservacoesDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite as observa��es do servi�o",
					AlertType.INFORMATION);

			textFieldLimiteMinimo.requestFocus();

			clienteServico = null;

		} else {

			if (getClienteServico() != null) {

				clienteServico.setIdClienteServico(getClienteServico().getIdClienteServico());

			}

			clienteServico.setIdCliente(cliente.getIdCliente());
			clienteServico.setNomeDoServico(textFieldNomeDoServico.getText());
			clienteServico.setCnpjDoServico(textFieldCnpjParaCobranca.getText());
			clienteServico.setProdutoDoServico(comboBoxProduto.getSelectionModel().getSelectedItem());
			clienteServico.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText()));
			clienteServico.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
			clienteServico.setObservacoesServico(textAreaObservacoesDoServico.getText());
			clienteServico.setSaldoServico(0);

		}

		return clienteServico;
	}

	// Getters and Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public ClienteServico getCompararClienteServico() {
		return compararClienteServico;
	}

	public void setCompararClienteServico(ClienteServico compararClienteServico) {
		this.compararClienteServico = compararClienteServico;
	}

	public static Scene getServicoFormScene() {
		return servicoFormScene;
	}

	public static void setServicoFormScene(Scene servicoFormScene) {
		ServicoFormController.servicoFormScene = servicoFormScene;
	}

}

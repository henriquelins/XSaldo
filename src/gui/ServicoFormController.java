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

	// Java variáveis

	private static Scene servicoFormScene;

	private Usuario usuario;

	private Cliente cliente;

	private ClienteServico clienteServico;

	private ClienteServico compararClienteServico;

	private ClienteServicoService service;

	private ClienteSelecionadoFormController clienteSelecionadoFormController;

	// Lista de ouvintes para receber alguma modificação

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// @FXML variáveis

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

	// Evento botão salvar ou editar serviço

	@FXML
	public void onButtonSalvarServicoAction(ActionEvent event) {

		setClienteServico(getFormData(cliente));

		boolean ok = compararCampos();

		if (ok == false) {

			service.clienteServicoNovoOuEditar(clienteServico);
			Utils.fecharTelaServicoFormAction();
			notifyDataChangeListeners();

		} else {

			Alerts.showAlert("Cadastro de serviços", "Editar serviços", "Não houve alteração no registro",
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
		lista.add("Crachás em pvc");
		lista.add("Crachás em pvc + acura");
		lista.add("Crachás em pvc + mifare");
		lista.add("Crachás em pvc adesivado");
		lista.add("Cartão pvc personalizado");
		lista.add("Cartão acura");
		lista.add("Cartão mifare");
		lista.add("Credencial");
		lista.add("Botton");

		return lista;

	}

	// Adiciona a lista um ouvinte, quando há uma modificação
	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Função que faz a atualização da tabela
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

	// Método com os objetos que devem ser inicializados

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

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Digite o nome do serviço",
					AlertType.INFORMATION);

			textFieldNomeDoServico.requestFocus();

			clienteServico = null;

		} else if (textFieldCnpjParaCobranca.getText() == null
				|| textFieldCnpjParaCobranca.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Digite o CNPJ do serviço",
					AlertType.INFORMATION);

			textFieldCnpjParaCobranca.requestFocus();

			clienteServico = null;

		} else if (comboBoxProduto.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Selecione o produto", AlertType.INFORMATION);

			comboBoxProduto.requestFocus();

			clienteServico = null;

		} else if (textFieldValorUnitario.getText() == null || textFieldValorUnitario.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Digite o valor unitário",
					AlertType.INFORMATION);

			textFieldValorUnitario.requestFocus();

			clienteServico = null;

		} else if (textFieldLimiteMinimo.getText() == null || textFieldLimiteMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Digite o limite mínimo",
					AlertType.INFORMATION);

			textFieldLimiteMinimo.requestFocus();

			clienteServico = null;

		} else if (textAreaObservacoesDoServico.getText() == null
				|| textAreaObservacoesDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de serviços", "Campo obrigatório", "Digite as observações do serviço",
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

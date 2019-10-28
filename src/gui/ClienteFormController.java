package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.Contato;
import model.entities.Endereco;
import model.entities.Usuario;
import model.services.ClienteService;

public class ClienteFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private static Scene clienteFormScene;

	private Cliente cliente;

	private Cliente clienteComparar;

	private ClienteService service;

	private PrincipalFormController principalFormController;
	
	private ClienteSelecionadoFormController clienteSelecionadoFormController;

	// Lista de ouvintes para receber alguma modificação

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// @FXML variáveis

	@FXML
	private Label lableTitulo;

	@FXML
	private TextField textFieldNomeFantasia;

	@FXML
	private TextField textFieldRazaoSocial;

	@FXML
	private TextField textFieldCnpj;

	@FXML
	private TextField textFieldInscEstadual;

	@FXML
	private TextField textFieldInscMunicipal;

	@FXML
	private DatePicker datePickerClienteDesde;

	@FXML
	private TextField textFieldContato;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textFieldFoneCelular;

	@FXML
	private TextField textFieldFoneFixo;

	@FXML
	private ComboBox<String> comboBoxVendedor;

	@FXML
	private ComboBox<String> comboBoxUnidadeAtendimento;

	@FXML
	private TextField textFieldLogradouro;

	@FXML
	private TextField textFieldBairro;

	@FXML
	private TextField textFieldCidade;

	@FXML
	private TextField textFieldCep;

	@FXML
	private ComboBox<String> comboBoxUnidadeFederal;

	@FXML
	private RadioButton radioButtonEntrega;

	@FXML
	private TextArea textAreaDetalhesDoCliente;

	@FXML
	private Button buttonSalvarCliente;

	// @FXML event

	@FXML
	public void onButtonSalvarClienteAction(ActionEvent event) {

		setCliente(getFormData());

		boolean ok = compararCampos();
		
		System.out.println(ok);

		if (ok == false) {

			service.clienteNovoOuEditar(cliente);
			Utils.fecharTelaClienteFormAction();
			notifyDataChangeListeners();

		} else {

			Alerts.showAlert("Cadastro de clientes", "Salvar cliente", "Erro cliente nulo", AlertType.INFORMATION);

		}

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

		principalFormController.updateTableView();
		clienteSelecionadoFormController.carregarCampos(cliente, null);

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		service = new ClienteService();

		principalFormController = new PrincipalFormController();
		
		clienteSelecionadoFormController = new ClienteSelecionadoFormController();

		lableTitulo.setText(Strings.getTitleCliente());
		comboBoxVendedor.setItems(FXCollections.observableArrayList(listaVendedores()));
		comboBoxUnidadeAtendimento.setItems(FXCollections.observableArrayList(listaUnidadesAtendimento()));
		comboBoxUnidadeFederal.setItems(FXCollections.observableArrayList(listaUnidadesFederacao()));

	}

	// Método carregar campos

	public void carregarCampos(Cliente cliente, Usuario usuario) {

		if (cliente != null) {

			textFieldNomeFantasia.setText(cliente.getNomeFantasia());
			textFieldRazaoSocial.setText(cliente.getRazaoSocial());
			textFieldCnpj.setText(cliente.getCnpjCliente());
			textFieldInscEstadual.setText(cliente.getInscricaoEstadual());
			textFieldInscMunicipal.setText(cliente.getInscricaoMunicipal());
			datePickerClienteDesde.setValue(Constraints.setDateToLocalDate(cliente.getDataInicioCliente()));
			textFieldContato.setText(cliente.getContato().contatoCliente);
			textFieldEmail.setText(cliente.getContato().getEmailCliente());
			textFieldFoneCelular.setText(cliente.getContato().getFoneCelular());
			textFieldFoneFixo.setText(cliente.getContato().getFoneFixo());
			comboBoxVendedor.getSelectionModel().select(cliente.getCod_vendedor());
			comboBoxUnidadeAtendimento.setValue(cliente.getUnidadeDeAtendimento());
			textFieldLogradouro.setText(cliente.getEndereco().getLogradouro());
			textFieldCidade.setText(cliente.getEndereco().getCidade());
			textFieldBairro.setText(cliente.getEndereco().getBairro());
			textFieldCep.setText(cliente.getEndereco().getCep());
			comboBoxUnidadeFederal.setValue(cliente.getEndereco().uf);
			radioButtonEntrega.selectedProperty().setValue(cliente.isEntregaNoCliente());
			textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente());

			setCliente(cliente);
			clienteComparar = cliente;

		} else {

			textFieldNomeFantasia.setText("");
			textFieldRazaoSocial.setText("");
			textFieldCnpj.setText("");
			textFieldInscEstadual.setText("");
			textFieldInscMunicipal.setText("");
			datePickerClienteDesde.setValue(null);
			textFieldContato.setText("");
			textFieldEmail.setText("");
			textFieldFoneCelular.setText("");
			textFieldFoneFixo.setText("");
			comboBoxVendedor.getSelectionModel().select(null);
			comboBoxUnidadeAtendimento.getSelectionModel().select(null);
			textFieldLogradouro.setText("");
			textFieldCidade.setText("");
			textFieldBairro.setText("");
			textFieldCep.setText("");
			comboBoxUnidadeFederal.setValue(null);
			radioButtonEntrega.selectedProperty().setValue(null);
			textAreaDetalhesDoCliente.setText("");

			setCliente(null);
			clienteComparar = null;

		}

	}

	// Método para testar os textFields

	private Cliente getFormData() {

		Cliente cliente = new Cliente();
		Contato contato = new Contato();
		Endereco endereco = new Endereco();

		if (textFieldNomeFantasia.getText() == null || textFieldNomeFantasia.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o nome do cliente",
					AlertType.INFORMATION);

			textFieldNomeFantasia.requestFocus();

			cliente = null;

		} else if (textFieldRazaoSocial.getText() == null || textFieldRazaoSocial.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a razão social do cliente",
					AlertType.INFORMATION);

			textFieldRazaoSocial.requestFocus();

			cliente = null;

		} else if (textFieldCnpj.getText() == null || textFieldCnpj.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o CNPJ do cliente",
					AlertType.INFORMATION);

			textFieldCnpj.requestFocus();

			cliente = null;

		} else if (datePickerClienteDesde.getValue() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campos obrigatório",
					"Selecione a data do início do cadastro do cliente", AlertType.INFORMATION);

			datePickerClienteDesde.requestFocus();

			cliente = null;

		} else if (textFieldContato.getText() == null || textFieldContato.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o nome do contato",
					AlertType.INFORMATION);

			textFieldContato.requestFocus();

			cliente = null;

		} else if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a descrição do produto",
					AlertType.INFORMATION);

			textFieldEmail.requestFocus();

			cliente = null;

		} else if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a descrição do produto",
					AlertType.INFORMATION);

			textFieldEmail.requestFocus();

			cliente = null;

		} else if ((textFieldFoneCelular.getText() == null || textFieldFoneCelular.getText().trim().equals(""))
				|| (textFieldFoneFixo.getText() == null || textFieldFoneFixo.getText().trim().equals(""))) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite ao menos 01 número de contato",
					AlertType.INFORMATION);

			textFieldFoneCelular.requestFocus();

			cliente = null;

		} else if (comboBoxVendedor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione o vendedor",
					AlertType.INFORMATION);

			comboBoxVendedor.requestFocus();

			cliente = null;

		} else if (comboBoxUnidadeAtendimento.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione a unidade de atendimento",
					AlertType.INFORMATION);

			comboBoxUnidadeAtendimento.requestFocus();

			cliente = null;

		} else if (textFieldLogradouro.getText() == null || textFieldLogradouro.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o logradouro do endereço do cliente",
					AlertType.INFORMATION);

			textFieldLogradouro.requestFocus();

			cliente = null;

		} else if (textFieldBairro.getText() == null || textFieldBairro.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o bairro do endereço do cliente",
					AlertType.INFORMATION);

			textFieldBairro.requestFocus();

			cliente = null;

		} else if (textFieldCidade.getText() == null || textFieldCidade.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a cidade do endereço do cliente",
					AlertType.INFORMATION);

			textFieldCidade.requestFocus();

			cliente = null;

		} else if (comboBoxUnidadeFederal.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione a unidade federal",
					AlertType.INFORMATION);

			comboBoxUnidadeFederal.requestFocus();

			cliente = null;

		} else {

			if (getCliente() != null) {

				cliente.setIdCliente(getCliente().getIdCliente());

			} else {
				
				
			}

			cliente.setNomeFantasia(textFieldNomeFantasia.getText());
			cliente.setRazaoSocial(textFieldRazaoSocial.getText());
			cliente.setCnpjCliente(textFieldCnpj.getText());
			cliente.setInscricaoEstadual(textFieldInscEstadual.getText());
			cliente.setInscricaoMunicipal(textFieldInscMunicipal.getText());
			cliente.setDataInicioCliente(Constraints.setLocalDateToDateSql(datePickerClienteDesde.getValue()));
			contato.setContatoCliente(textFieldContato.getText());
			contato.setEmailCliente(textFieldEmail.getText());
			contato.setFoneCelular(textFieldFoneCelular.getText());
			contato.setFoneFixo(textFieldFoneFixo.getText());
			cliente.setCod_vendedor(comboBoxVendedor.getSelectionModel().getSelectedIndex());
			cliente.setUnidadeDeAtendimento(comboBoxUnidadeAtendimento.getSelectionModel().getSelectedItem());
			endereco.setLogradouro(textFieldLogradouro.getText());
			endereco.setBairro(textFieldBairro.getText());
			endereco.setCidade(textFieldCidade.getText());
			endereco.setCep(textFieldCep.getText());
			endereco.setUf(comboBoxUnidadeFederal.getSelectionModel().getSelectedItem());
			cliente.setEntregaNoCliente(radioButtonEntrega.isSelected());
			cliente.setDetalhesDoCliente(textAreaDetalhesDoCliente.getText());

			cliente.setContato(contato);
			cliente.setEndereco(endereco);

		}

		return cliente;

	}

	// Listas de comboBox

	// lista vendedores

	private List<String> listaVendedores() {

		List<String> lista = new ArrayList<>();
		lista.add("Conecta - Caruaru");
		lista.add("Conecta - Recife");
		lista.add("Helton");

		return lista;

	}

	// lista unidades

	private List<String> listaUnidadesAtendimento() {

		List<String> lista = new ArrayList<>();
		lista.add("Conecta - Caruaru");
		lista.add("Conecta - Recife");

		return lista;

	}

	// lista uf

	private List<String> listaUnidadesFederacao() {

		List<String> lista = new ArrayList<>();

		String[] uf = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
				"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };

		for (String e : uf) {

			lista.add(e);

		}

		return lista;

	}

	// Comparar todos os campos

	private boolean compararCampos() {

		boolean ok = false;
		
		
		System.out.println(clienteComparar.toString());
		
		System.out.println(cliente.toString());

		if (clienteComparar == null) {

			return ok;

		} else if (cliente.equals(clienteComparar)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Getters and Setters

	public Cliente getCliente() {
		
		return cliente;
		
	}

	public void setCliente(Cliente cliente) {
		
		this.cliente = cliente;
		
	}

	public ClienteService getService() {
		
		return service;
		
	}

	public void setService(ClienteService service) {
		
		this.service = service;
		
	}

	public static Scene getClienteFormScene() {
		
		return clienteFormScene;
		
	}

	public static void setClienteScene(Scene clienteFormScene) {
		
		ClienteFormController.clienteFormScene = clienteFormScene;
		
	}

}

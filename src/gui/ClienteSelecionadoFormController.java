package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.forms.Forms;
import gui.listeners.DataChangeListener;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.ClienteServico;
import model.entities.Usuario;
import model.services.ClienteService;
import model.services.ClienteServicoService;

public class ClienteSelecionadoFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private Usuario usuario;

	private Cliente cliente;

	private ClienteServicoService clienteService;

	private ClienteServico clienteServico;
	
	private PrincipalFormController principalFormController;
	
	private static int id_cliente_servico;

	// @FXML variáveis

	@FXML
	private Label labelTituloCliente;

	@FXML
	private Label labelNomeFantasia;

	@FXML
	private Label labelRazaoSocial;

	@FXML
	private Label labelCnpj;

	@FXML
	private Label labelInscricaoEstadual;

	@FXML
	private Label labelInscricaoMuncipal;

	@FXML
	private Label labelClienteDesde;

	@FXML
	private Label labelContato;

	@FXML
	private Label labelEmail;

	@FXML
	private Label labelFoneCelular;

	@FXML
	private Label labelFoneFixo;

	@FXML
	private Label labelVendedor;

	@FXML
	private Label labelUnidadeDeAtendimento;

	@FXML
	private Label labelLogradouro;

	@FXML
	private Label labelBairro;

	@FXML
	private Label labelCidade;

	@FXML
	private Label labelCep;

	@FXML
	private Label labelUf;

	@FXML
	private Label labelEntrega;

	@FXML
	private TextArea textAreaDetalhesDoCliente;

	@FXML
	private ComboBox<String> comboBoxServico;

	@FXML
	private TextField textFieldServico;

	@FXML
	private TextField textFieldSaldo;

	@FXML
	private TextField textFieldValorUnitario;

	@FXML
	private TextField textFieldLimiteMinimo;

	@FXML
	private TextField textFieldCnpj;

	@FXML
	private TextArea textAreaDetalhesDoServico;

	@FXML
	private Button buttonNovoServico;

	@FXML
	private Button buttonEditarServico;

	@FXML
	private Button buttonFazerLancamento;

	@FXML
	private Button buttonVerLancamentos;

	@FXML
	private Button buttonEditarCliente;

	// @FXML event

	// Evento botão novo serviço

	@FXML
	public void onButtonNovoServicoAction(ActionEvent event) {

		new Forms().servicoForm(usuario, cliente, null, Strings.getServicoView());

	}

	// Evento botão editar serviço

	@FXML
	public void onButtonEditarServicoAction(ActionEvent event) {

		if (comboBoxServico.getSelectionModel().equals(null) == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviço não cadastrado", "Adicione um servico", AlertType.ERROR);

		} else {

			if (clienteServico != null) {

				new Forms().servicoForm(usuario, cliente, clienteServico, Strings.getServicoView());

			} else {

				Alerts.showAlert("Serviços do Cliente", "Serviço não selecionado", "Selecione um servico",
						AlertType.ERROR);

			}
		}

	}

	// Evento botão fazer lançamento

	@FXML
	public void onButtonFazerLancamentoAction(ActionEvent event) {

		if (comboBoxServico.getSelectionModel().equals(null) == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviço não cadastrado", "Adicione um servico", AlertType.ERROR);

		} else {

			if (clienteServico != null) {

				new Forms().lancamentoForm(usuario, cliente, clienteServico, Strings.getLancamentoView());

			} else {

				Alerts.showAlert("Serviços do Cliente", "Serviço não selecionado", "Selecione um servico",
						AlertType.ERROR);

			}
		}

	}

	// Evento botão ver lançamento

	@FXML
	public void onButtonVerLancamentoAction(ActionEvent event) {

		if (comboBoxServico.getSelectionModel().equals(null) == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviço não cadastrado", "Adicione um servico", AlertType.ERROR);

		} else {

			if (clienteServico != null) {

				new Forms().lancamentoListForm(usuario, cliente, clienteServico, Strings.getLancamentoListView());

			} else {

				Alerts.showAlert("Serviços do Cliente", "Serviço não selecionado", "Selecione um servico",
						AlertType.ERROR);

			}
		}

	}

	// Evento botão editar cliente serviço

	@FXML
	public void onButtoEditarClienteServicoAction(ActionEvent event) {

		clienteForm(usuario, cliente, Strings.getClienteView());

	}

	// Evento selecionar o serviço do comboBox

	@FXML
	public void onComboBoxServicoAction(ActionEvent event) {

		if (comboBoxServico.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Serviços do Cliente", "Serviço não Cadastratado", "Adicione um servico", AlertType.ERROR);

			clienteServico = null;

		} else {

			String servico = comboBoxServico.getSelectionModel().getSelectedItem();
			id_cliente_servico = servico_para_id_cliente_servico(servico);
			setClienteServico(clienteService.buscarPeloIdCliente(id_cliente_servico));
			carregarCamposClienteServico();
			//carregarCamposClienteServico(getClienteServico());
	
		}

	}

	// Lista de serviços

	private List<String> listaServicos() {

		ClienteServicoService clienteService = new ClienteServicoService();
		List<String> listaClienteService = new ArrayList<>();

		for (ClienteServico cs : clienteService.buscarServicosDoCliente(cliente.getIdCliente())) {

			listaClienteService.add(cs.getIdClienteServico() + " - " + cs.getNomeDoServico());

		}

		if (listaClienteService.isEmpty() == true) {

			listaClienteService.add("Adicione um servico");

			return listaClienteService;

		} else {

			return listaClienteService;

		}

	}

	// Método para atualizar comboBox serviços

	public void updateDadosServicos() {
		
		principalFormController.updateTableView();
		
		setClienteServico(clienteService.buscarPeloIdCliente(id_cliente_servico));
		
		carregarCamposClienteServico();
		
		//carregarCamposClienteServico();

	}

	// Listner

	@Override
	public void onDataChanged() {

		updateDadosServicos();

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		clienteService = new ClienteServicoService();
		
		principalFormController = new PrincipalFormController();

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, Usuario usuario) {

		labelTituloCliente.setText(cliente.getNomeFantasia());
		labelNomeFantasia.setText(cliente.getNomeFantasia());
		labelRazaoSocial.setText(cliente.getRazaoSocial());
		labelCnpj.setText(cliente.getCnpjCliente());
		labelInscricaoEstadual.setText(cliente.getInscricaoEstadual());
		labelInscricaoMuncipal.setText(cliente.getInscricaoMunicipal());
		labelClienteDesde.setText(cliente.getDataInicioCliente().toString());
		labelContato.setText(cliente.getContato().getContatoCliente());
		labelEmail.setText(cliente.getContato().getEmailCliente());
		labelFoneCelular.setText(cliente.getContato().getFoneCelular());
		labelFoneFixo.setText(cliente.getContato().getFoneFixo());
		labelVendedor.setText(String.valueOf(cliente.getCod_vendedor()));
		labelUnidadeDeAtendimento.setText(cliente.getUnidadeDeAtendimento());
		labelLogradouro.setText(cliente.getEndereco().getLogradouro());
		labelBairro.setText(cliente.getEndereco().getBairro());
		labelCidade.setText(cliente.getEndereco().getCidade());
		labelCep.setText(cliente.getEndereco().getCep());
		labelUf.setText(cliente.getEndereco().getUf());
		labelEntrega.setText(String.valueOf(cliente.isEntregaNoCliente()));
		textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente());

		setCliente(cliente);
		setUsuario(usuario);

		comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));

	}

	// Converter String para inteiro

	public int servico_para_id_cliente_servico(String servico) {

		int index = servico.indexOf("-");
		String id_cliente_servico = servico.substring(0, index - 1);

		return Integer.valueOf(id_cliente_servico);

	}

	// Carregar os campos ClienteServico

	public void carregarCamposClienteServico() {
	
		textFieldServico.setText(clienteServico.getNomeDoServico());
		textFieldSaldo.setText(String.valueOf(clienteServico.getSaldoServico()));
		textFieldValorUnitario.setText(String.valueOf(clienteServico.getValorUnitario()));
		textFieldLimiteMinimo.setText(String.valueOf(clienteServico.getLimiteMinimo()));
		textFieldCnpj.setText(clienteServico.getCnpjDoServico());
		textAreaDetalhesDoServico.setText(clienteServico.getObservacoesServico());

	}
	
	// clienteForm com DataChangeList

		public void clienteForm(Usuario usuario, Cliente cliente, String tela) {

			boolean concedido = false;
			Acesso acesso = new Acesso();

			concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

			if (concedido == true) {

				try {

					FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
					ScrollPane pane = loader.load();

					ClienteFormController controller = loader.getController();
					controller.setService(new ClienteService());
					controller.subscribeDataChangeListener(this);
					controller.carregarCampos(cliente, usuario);

					ClienteFormController.setClienteScene(new Scene(pane));

					Stage primaryStage = new Stage();
					primaryStage.setTitle(Strings.getTitle());
					primaryStage.setScene(ClienteFormController.getClienteFormScene());
					primaryStage.setResizable(true);
					primaryStage.initModality(Modality.APPLICATION_MODAL);

					pane.setFitToHeight(true);
					pane.setFitToWidth(true);

					primaryStage.setResizable(true);
					primaryStage.setMaximized(true);

					Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
					primaryStage.getIcons().add(applicationIcon);

					primaryStage.showAndWait();

				} catch (IOException e) {

					Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

				}

			} else {

				Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", "Entre em contato com o Administrador do sistema" , AlertType.ERROR);

			}

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

}

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
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Usuario;
import model.services.ClienteService;

public class PrincipalFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private static int id_cliente;

	private Usuario usuario;

	private Cliente cliente;

	private ClienteService service;

	private static ObservableList<Cliente> listaClientes;

	// @FXML variáveis

	@FXML
	private Label labelLogado;

	@FXML
	private Button buttonNovoCliente;

	@FXML
	private Button buttonPesquisar;

	@FXML
	private TextField textFieldPesquisar;

	@FXML
	private ComboBox<String> comboBoxListarClientes;

	@FXML
	private MenuItem menuItemLogout;

	@FXML
	private MenuItem menuItemSair;

	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemCliente;

	@FXML
	private MenuItem menuItemLancamentoList;

	@FXML
	private MenuItem menuItemAjuda;

	@FXML
	public TableView<Cliente> tableViewCliente;

	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;

	@FXML
	private TableColumn<Cliente, String> tableColumnNomeFantasia;

	@FXML
	private TableColumn<Cliente, String> tableColumnRazaoSocial;

	@FXML
	private TableColumn<Cliente, String> tableColumnCnpj;

	@FXML
	private TableColumn<Cliente, String> tableColumnAtendimento;

	@FXML
	private TableColumn<Cliente, String> tableColumnEmail;

	@FXML
	private TableColumn<Cliente, String> tableColumnFone;

	@FXML
	private TableColumn<Cliente, Cliente> tableColumnClienteSelecionado;

	// @FXML event
	
	// evento botão cliente 
	
	@FXML
	public void onBtClienteAction(ActionEvent event) {

		clienteForm(usuario, null, Strings.getClienteView());

	}
	
	// evento botão pesquisar

	@FXML
	public void onBtPesquisarClienteAction(ActionEvent event) {

		Alerts.showAlert("Em breve", "Em desenvolvimento", null, AlertType.ERROR);

	}
	
	// evento menu item logout

	@FXML
	public void onMenuItemLogoutAction(ActionEvent event) {

		Utils.fecharTelaPrincipalFormAction();
		new Forms().loginForm(Strings.getLoginView());

	}
	
	// evento menu item sair

	@FXML
	public void onMenuItemSairAction(ActionEvent event) {

		System.exit(0);

	}
	
	// evento menu item usuario

	@FXML
	public void onMenuItemUsuarioAction(ActionEvent event) {

		new Forms().usuarioForm(usuario, Strings.getUsuarioView());

	}
	
	// evento menu item cliente

	@FXML
	public void onMenuItemClienteAction(ActionEvent event) {

		clienteForm(usuario, null, Strings.getClienteView());

	}

	@FXML
	public void onMenuItemAjudaAction(ActionEvent event) {

		new Forms().sobreForm(Strings.getSobreView());

	}
	
	// evento comboBox listar

	@FXML
	public void onComboBoxListarClientesAction(ActionEvent event) {
	
		try {

			if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("Todos")) {

				updateTableView();

			

			} else if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("Limpar")) {

				clearTableView();

				
			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Lista Clientes", "Lista Vazia", "java.lang.NullPointerException", AlertType.ERROR);
			
		}

	}

	// Listener

	@Override
	public void onDataChanged() {

		updateTableView();

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		service = new ClienteService();

		comboBoxListarClientes.setItems(FXCollections.observableArrayList(listarClientes()));

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
		tableColumnNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		tableColumnRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
		tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpjCliente"));
		tableColumnAtendimento.setCellValueFactory(new PropertyValueFactory<>("unidadeDeAtendimento"));
		tableColumnEmail.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getContato().getEmailCliente()));
		tableColumnFone.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getContato().getFoneCelular()));

	}

	// Lista todos os clientes

	private void listaTodos() {

		listaClientes = FXCollections.observableArrayList(service.findAll());

	}

	// Atualizar Table
	
	public void updateTableView() {
		
		
		listaTodos();
		tableViewCliente.setItems(listaClientes);

		initClienteSelecionadoButton();

		/*if (service == null) {

			throw new IllegalStateException("clienteService está nulo");

		} else {

			listaTodos();
			tableViewCliente.setItems(listaClientes);

			initClienteSelecionadoButton();
			
		}*/

	}

	// Limpar Table
	
	public void clearTableView() {

		if (service == null) {

			throw new IllegalStateException("clienteService está nulo");

		}

		tableViewCliente.setItems(null);

		initClienteSelecionadoButton();
	
	}

	// Iniciar button on Table - botão detalhes

	private void initClienteSelecionadoButton() {

		tableColumnClienteSelecionado.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnClienteSelecionado.setCellFactory(param -> new TableCell<Cliente, Cliente>() {

			private final Button button = new Button("Selecionar");

			@Override
			protected void updateItem(Cliente cliente, boolean empty) {

				super.updateItem(cliente, empty);

				if (cliente == null) {

					setGraphic(null);
					return;

				} else {
									
					setGraphic(button);
					button.setOnAction(event -> new Forms().clienteSelecionadoForm(usuario, cliente,
							Strings.getClienteSelecionadoView()));

				}

			}
		});
	}

	// Lista do comboBox

	private List<String> listarClientes() {

		List<String> lista = new ArrayList<>();
		lista.add("Todos");
		lista.add("Limpar");

		return lista;

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

	public Label getLabelLogado() {
		return labelLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setLabelLogado(String logado) {
		this.labelLogado.setText(logado);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static int getId_cliente() {
		return id_cliente;
	}

	public static void setId_cliente(int id_cliente) {
		PrincipalFormController.id_cliente = id_cliente;
	}

}

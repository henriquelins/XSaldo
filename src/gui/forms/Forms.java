package gui.forms;

import java.io.IOException;

import application.Main;
import gui.ClienteSelecionadoFormController;
import gui.LancamentoFormController;
import gui.LancamentoListFormController;
import gui.PrincipalFormController;
import gui.ServicoFormController;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.entities.Cliente;
import model.entities.ClienteServico;
import model.entities.Usuario;

public class Forms {

	public void splashForm(String tela) throws IOException {

		try {

			StackPane pane = FXMLLoader.load(getClass().getResource(tela));

			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

			// Carrega a tela splash com fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(2);
			fadeIn.setCycleCount(1);

			// Termina a tela splash com fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(2);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			// Depois de fade in, inicia o fade out
			fadeIn.setOnFinished((e) -> {

				fadeOut.play();

			});

			// Depois do fade out, carrega a tela inicial - login
			fadeOut.setOnFinished((e) -> {

				primaryStage.close();
				loginForm(Strings.getLoginView());

			});

		} catch (IOException ex) {

			Alerts.showAlert("Controle de Estoque", "Erro ao abrir o splash", ex.getLocalizedMessage(),
					AlertType.ERROR);

		}

	}

	public void loginForm(String tela) {

		try {

			AnchorPane pane = FXMLLoader.load(getClass().getResource((tela)));

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}

	public void principalForm(Usuario usuario, String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			ScrollPane pane = loader.load();

			Main.setMainScene(new Scene(pane));

			PrincipalFormController controller = loader.getController();
			controller.setLabelLogado(usuario.toUsuarioLogado());
			controller.setUsuario(usuario);

			pane.setFitToHeight(true);
			pane.setFitToWidth(true);

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(Main.getMainScene());

			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela principal", e.getCause().toString(),
					AlertType.ERROR);

		}

	}

	public void clienteSelecionadoForm(Usuario usuario, Cliente cliente, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				ClienteSelecionadoFormController controller = loader.getController();
				controller.carregarCampos(cliente, usuario);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.setResizable(true);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				primaryStage.setResizable(true);
				primaryStage.setMaximized(true);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	public void usuarioForm(Usuario usuario, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.setResizable(false);
				primaryStage.initModality(Modality.APPLICATION_MODAL);
				primaryStage.initOwner(null);

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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	public void sobreForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

		}

	}

	public void lancamentoListForm(Usuario usuario, Cliente cliente, ClienteServico clienteServico, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				LancamentoListFormController controller = loader.getController();
				controller.carregarCampos(cliente, clienteServico, usuario);
				
				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));

				primaryStage.setResizable(true);
				primaryStage.setMaximized(true);
				
				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	public void lancamentoForm(Usuario usuario, Cliente cliente, ClienteServico clienteServico, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				LancamentoFormController controller = loader.getController();
				controller.carregarCampos(cliente, clienteServico, usuario);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);
				
				LancamentoFormController.setLancamentoFormScene(new Scene(pane));
				
				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(LancamentoFormController.getLancamentoFormScene());
				primaryStage.setResizable(true);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				primaryStage.setResizable(true);
				primaryStage.setMaximized(true);
				
				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	public void servicoForm(Usuario usuario, Cliente cliente, ClienteServico clienteServico, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				ServicoFormController controller = loader.getController();
				controller.carregarCampos(cliente, clienteServico, usuario);
				
				pane.setFitToHeight(true);
				pane.setFitToWidth(true);
				
				ServicoFormController.setServicoFormScene(new Scene(pane));

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(ServicoFormController.getServicoFormScene());
				primaryStage.setResizable(true);
				primaryStage.initModality(Modality.APPLICATION_MODAL);
				
				primaryStage.setResizable(true);
				primaryStage.setMaximized(true);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

}

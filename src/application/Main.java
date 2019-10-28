package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import db.DB;
import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import properties.PropertiesFile;

public class Main extends Application {

	private static Scene mainScene;

	private static Socket socket;

	private static ServerSocket serverSocket;

	private static int portSocket;
	
	public static String style = "/application/darktheme.css";
	
	@Override
	public void start(Stage primaryStage) throws SQLException {

		Connection conn = null;
		conn = DB.getConnection();

		if (conn != null) {

			try {

				// impede que seja criada uma nova inst�ncia do programa
				portSocket = Integer
						.parseInt(PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort()));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));

				try {

					// Define o Style
					setUserAgentStylesheet(STYLESHEET_CASPIAN);
					//setUserAgentStylesheet(STYLESHEET_MODENA);
					
					//Application.setUserAgentStylesheet(getClass().getResource(style).toExternalForm());
					

					new Forms().splashForm(Strings.getSplashView());

				} catch (Exception e) {

					Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"J� existe uma inst�ncia do programa aberta!", AlertType.ERROR);

			}

		} else {

			// primaryStage.close();
			// login(primaryStage, Strings.getLoginView());

		}

	}
	
	
	//Getters and Setters

	public static Scene getMainScene() {

		return mainScene;

	}

	public static void setMainScene(Scene mainScene) {

		Main.mainScene = mainScene;

	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {

		Main.socket = socket;

	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {

		Main.serverSocket = serverSocket;

	}

	
	// inicia o aplicativo

	public static void main(String[] args) {

		launch(args);

	}

}

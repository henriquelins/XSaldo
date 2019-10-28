package gui.util;

import java.sql.Date;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Constraints {

	// Passar string para int

	public static Integer tryParseToInt(String str) {

		try {

			return Integer.parseInt(str);

		} catch (NumberFormatException e) {

			return null;

		}

	}

	// Passar textfields para int

	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*")) {
				txt.setText(oldValue);
			}
		});
	}

	// Definir o tamanho max do textField

	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}

	// Passar textfields para double

	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(oldValue);
			}
		});
	}

	// Definir o tamanho max do passwordField

	public static void setPasswordFieldMaxLength(PasswordField psw, int max) {
		psw.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				psw.setText(oldValue);
			}
		});
	}

	// Passar localDate para SqlDate

	public static Date setLocalDateToDateSql(LocalDate localDate) {

		Date clienteDesde = java.sql.Date.valueOf(localDate);
		return clienteDesde;

	}
	
	
	// Passar Date para localDate

		public static LocalDate setDateToLocalDate(Date date) {

			LocalDate clienteDesde = date.toLocalDate();
			return clienteDesde;

		}

	// Passar SqlDate para string formatada

	public static String setDateFormat(Date Data) {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formato.format(Data);

		return dataFormatada;

	}

}
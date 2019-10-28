package gui.util;

public class Acesso {

	public boolean concederAcesso(Integer acesso, String tela) {

		boolean concedido = false;

		if (acesso == 1 && tela.equalsIgnoreCase(Strings.getUsuarioView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getClienteView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getLancamentoListView())) {

			concedido = true;

		}  else if (acesso == 1 && tela.equalsIgnoreCase( Strings.getClienteSelecionadoView()) || acesso == 2 && tela.equalsIgnoreCase( Strings.getClienteSelecionadoView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getLancamentoView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getServicoView())) {

			concedido = true;

		} else {

			concedido = false;

		}

		return concedido;

	}

}

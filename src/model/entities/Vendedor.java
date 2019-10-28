package model.entities;

public class Vendedor {

	private int id_vendedor;
	private String nome;
	private String cargo;
	private String foneComercial;
	private String emailComercial;
	private String fonePessoal;
	private String emailPessoal;
	private String login;
	private String senha;
	private String acesso;

	public Vendedor() {
	}

	public Vendedor(int id_vendedor, String nome, String cargo, String foneComercial, String emailComercial,
			String fonePessoal, String emailPessoal, String login, String senha, String acesso) {
		super();
		this.id_vendedor = id_vendedor;
		this.nome = nome;
		this.cargo = cargo;
		this.foneComercial = foneComercial;
		this.emailComercial = emailComercial;
		this.fonePessoal = fonePessoal;
		this.emailPessoal = emailPessoal;
		this.login = login;
		this.senha = senha;
		this.acesso = acesso;
	}

	public int getId_vendedor() {
		return id_vendedor;
	}

	public void setId_vendedor(int id_vendedor) {
		this.id_vendedor = id_vendedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getFoneComercial() {
		return foneComercial;
	}

	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}

	public String getEmailComercial() {
		return emailComercial;
	}

	public void setEmailComercial(String emailComercial) {
		this.emailComercial = emailComercial;
	}

	public String getFonePessoal() {
		return fonePessoal;
	}

	public void setFonePessoal(String fonePessoal) {
		this.fonePessoal = fonePessoal;
	}

	public String getEmailPessoal() {
		return emailPessoal;
	}

	public void setEmailPessoal(String emailPessoal) {
		this.emailPessoal = emailPessoal;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAcesso() {
		return acesso;
	}

	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}

	@Override
	public String toString() {
		return "Vendedor [id_vendedor=" + id_vendedor + ", nome=" + nome + ", "
				+ "cargo=" + cargo + ", foneComercial=" + foneComercial + ", "
				+ "emailComercial=" + emailComercial + ", fonePessoal=" + fonePessoal
				+ ", emailPessoal=" + emailPessoal + ", login=" + login + ", "
				+ "senha=" + senha + ", acesso=" + acesso + "]";
	}
	
	

	
}

package model.entities;

public class ClienteServico {

	private Integer idClienteServico;
	private Integer idCliente;
	private String nomeDoServico;
	private String produtoDoServico;
	private String cnpjDoServico;
	private Integer saldoServico;
	private String observacoesServico;
	private Integer limiteMinimo;	
	private double valorUnitario;

	public ClienteServico() {}

	public Integer getIdClienteServico() {
		return idClienteServico;
	}

	public void setIdClienteServico(Integer idClienteServico) {
		this.idClienteServico = idClienteServico;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeDoServico() {
		return nomeDoServico;
	}

	public void setNomeDoServico(String nomeDoServico) {
		this.nomeDoServico = nomeDoServico;
	}

	public String getProdutoDoServico() {
		return produtoDoServico;
	}

	public void setProdutoDoServico(String produtoDoServico) {
		this.produtoDoServico = produtoDoServico;
	}

	public String getCnpjDoServico() {
		return cnpjDoServico;
	}

	public void setCnpjDoServico(String cnpjDoServico) {
		this.cnpjDoServico = cnpjDoServico;
	}

	public Integer getSaldoServico() {
		return saldoServico;
	}

	public void setSaldoServico(Integer saldoServico) {
		this.saldoServico = saldoServico;
	}

	public String getObservacoesServico() {
		return observacoesServico;
	}

	public void setObservacoesServico(String observacoesServico) {
		this.observacoesServico = observacoesServico;
	}

	public Integer getLimiteMinimo() {
		return limiteMinimo;
	}

	public void setLimiteMinimo(Integer limiteMinimo) {
		this.limiteMinimo = limiteMinimo;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpjDoServico == null) ? 0 : cnpjDoServico.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idClienteServico == null) ? 0 : idClienteServico.hashCode());
		result = prime * result + ((limiteMinimo == null) ? 0 : limiteMinimo.hashCode());
		result = prime * result + ((nomeDoServico == null) ? 0 : nomeDoServico.hashCode());
		result = prime * result + ((observacoesServico == null) ? 0 : observacoesServico.hashCode());
		result = prime * result + ((produtoDoServico == null) ? 0 : produtoDoServico.hashCode());
		result = prime * result + ((saldoServico == null) ? 0 : saldoServico.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorUnitario);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteServico other = (ClienteServico) obj;
		if (cnpjDoServico == null) {
			if (other.cnpjDoServico != null)
				return false;
		} else if (!cnpjDoServico.equals(other.cnpjDoServico))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idClienteServico == null) {
			if (other.idClienteServico != null)
				return false;
		} else if (!idClienteServico.equals(other.idClienteServico))
			return false;
		if (limiteMinimo == null) {
			if (other.limiteMinimo != null)
				return false;
		} else if (!limiteMinimo.equals(other.limiteMinimo))
			return false;
		if (nomeDoServico == null) {
			if (other.nomeDoServico != null)
				return false;
		} else if (!nomeDoServico.equals(other.nomeDoServico))
			return false;
		if (observacoesServico == null) {
			if (other.observacoesServico != null)
				return false;
		} else if (!observacoesServico.equals(other.observacoesServico))
			return false;
		if (produtoDoServico == null) {
			if (other.produtoDoServico != null)
				return false;
		} else if (!produtoDoServico.equals(other.produtoDoServico))
			return false;
		if (saldoServico == null) {
			if (other.saldoServico != null)
				return false;
		} else if (!saldoServico.equals(other.saldoServico))
			return false;
		if (Double.doubleToLongBits(valorUnitario) != Double.doubleToLongBits(other.valorUnitario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteServico [idClienteServico=" + idClienteServico + ", idCliente=" + idCliente + ", nomeDoServico="
				+ nomeDoServico + ", produtoDoServico=" + produtoDoServico + ", cnpjDoServico=" + cnpjDoServico
				+ ", saldoServico=" + saldoServico + ", observacoesServico=" + observacoesServico + ", limiteMinimo="
				+ limiteMinimo + ", valorUnitario=" + valorUnitario + "]";
	}

	
}

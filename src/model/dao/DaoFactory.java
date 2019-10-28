package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.ClienteServicoDaoJDBC;
import model.dao.impl.ContatoDaoJDBC;
import model.dao.impl.EnderecoDaoJDBC;
import model.dao.impl.LancamentoDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

	public static UsuarioDao createUsuarioDao() {

		return new UsuarioDaoJDBC(DB.getConnection());

	}

	public static ClienteDao createClienteDao() {

		return new ClienteDaoJDBC(DB.getConnection());

	}
	
	public static ClienteServicoDao createClienteServicoDao() {

		return new ClienteServicoDaoJDBC(DB.getConnection());

	}
	
	public static ContatoDao createContatoDao() {

		return new ContatoDaoJDBC(DB.getConnection());

	}
	
	public static EnderecoDao createEnderecoDao() {

		return new EnderecoDaoJDBC(DB.getConnection());

	}

	public static LancamentoDao createLancamentoDao() {

		return new LancamentoDaoJDBC(DB.getConnection());

	}


}

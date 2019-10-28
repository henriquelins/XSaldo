package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import model.dao.ContatoDao;
import model.entities.Cliente;

public class ContatoDaoJDBC implements ContatoDao {
	
	private Connection conn;
	
	public ContatoDaoJDBC(Connection conn) {
		this.conn = conn;
	}


	public void inserir(Cliente cliente) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO contato (contato_cliente, email_cliente, fone_celular, fone_fixo, id_cliente) "
							+ "VALUES (?, ?, ? ,?, ?)");

			st.setString(1, cliente.getContato().getContatoCliente());
			st.setString(2, cliente.getContato().getEmailCliente());
			st.setString(3, cliente.getContato().getFoneCelular());
			st.setString(4, cliente.getContato().getFoneFixo());
			st.setInt(5, cliente.getIdCliente());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {

				throw new DbException("Error ao inserir o contato do cliente! Nenhuma linha afetada no processo!");

			} 

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transa��o rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
		}

	}


}

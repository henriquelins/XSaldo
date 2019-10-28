package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import model.dao.EnderecoDao;
import model.entities.Cliente;

public class EnderecoDaoJDBC implements EnderecoDao {

	private Connection conn;

	public EnderecoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Cliente cliente) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO endereco (logradouro, bairro, cidade, uf, cep, id_cliente) "
					+ "VALUES (?, ?, ? ,?, ?, ?)");

			st.setString(1, cliente.getEndereco().getLogradouro());
			st.setString(2, cliente.getEndereco().getBairro());
			st.setString(3, cliente.getEndereco().getCidade());
			st.setString(4, cliente.getEndereco().getUf());
			st.setString(5, cliente.getEndereco().getCep());
			st.setInt(6, cliente.getIdCliente());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {

				throw new DbException("Error ao inserir o endereço do cliente! Nenhuma linha afetada no processo!");

			} 

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
		}

	}

}

package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClienteServicoDao;
import model.entities.ClienteServico;

public class ClienteServicoDaoJDBC implements ClienteServicoDao {

	private Connection conn;

	public ClienteServicoDaoJDBC(Connection conn) {

		this.conn = conn;

	}

	@Override
	public void inserir(ClienteServico clienteServico) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO cliente_servico (id_cliente, nome_servico, produto_servico, cnpj_servico, saldo_servico,"
							+ " observacoes_servico, limite_minimo, valor_unitario)"
							+ " VALUES (?, ?, ? ,?, ?, ?, ?, ?)");

			st.setInt(1, clienteServico.getIdCliente());
			st.setString(2, clienteServico.getNomeDoServico());
			st.setString(3, clienteServico.getProdutoDoServico());
			st.setString(4, clienteServico.getCnpjDoServico());
			st.setInt(5, clienteServico.getSaldoServico());
			st.setString(6, clienteServico.getObservacoesServico());
			st.setInt(7, clienteServico.getLimiteMinimo());
			st.setDouble(8, clienteServico.getValorUnitario());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Error ao inserir o serviço! Nenhuma linha afetada no processo!");

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

	@Override
	public void atualizar(ClienteServico clienteServico) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE cliente_servico SET id_cliente = ?, nome_servico = ?, produto_servico = ?, cnpj_servico = ?, "
							+ "saldo_servico = ?, observacoes_servico = ?, limite_minimo = ?, valor_unitario = ? "
							+ "WHERE id_cliente_servico = ?");

			st.setInt(1, clienteServico.getIdCliente());
			st.setString(2, clienteServico.getNomeDoServico());
			st.setString(3, clienteServico.getProdutoDoServico());
			st.setString(4, clienteServico.getCnpjDoServico());
			st.setInt(5, clienteServico.getSaldoServico());
			st.setString(6, clienteServico.getObservacoesServico());
			st.setInt(7, clienteServico.getLimiteMinimo());
			st.setDouble(8, clienteServico.getValorUnitario());
			st.setInt(9, clienteServico.getIdClienteServico());

			st.executeUpdate();

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

	@Override
	public ClienteServico buscarPeloIdCliente(Integer id_cliente_servico) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM cliente_servico WHERE id_cliente_servico = ? ORDER BY id_cliente_servico");

			st.setInt(1, id_cliente_servico);
			rs = st.executeQuery();

			if (rs.next()) {

				ClienteServico clienteServico = instantiateClienteServico(rs);

				return clienteServico;

			}

			conn.commit();

			return null;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<ClienteServico> buscarServicosDoCliente(Integer idCliente) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM cliente_servico WHERE id_cliente = ? ORDER BY id_cliente");
			st.setInt(1, idCliente);
			rs = st.executeQuery();

			List<ClienteServico> list = new ArrayList<>();

			while (rs.next()) {

				ClienteServico clienteServico = instantiateClienteServico(rs);
				list.add(clienteServico);

			}

			conn.commit();

			return list;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public void atualizarSaldo(int saldoAtual, int idClienteServico) {

		PreparedStatement st = null;
		
		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE cliente_servico SET saldo_servico = ? WHERE id_cliente_servico = ?");

			st.setInt(1, saldoAtual);
			st.setInt(2, idClienteServico);

			st.executeUpdate();

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

	private ClienteServico instantiateClienteServico(ResultSet rs) throws SQLException {

		ClienteServico clienteServico = new ClienteServico();

		clienteServico.setIdClienteServico(rs.getInt("id_cliente_servico"));
		clienteServico.setIdCliente(rs.getInt("id_cliente"));
		clienteServico.setNomeDoServico(rs.getString("nome_servico"));
		clienteServico.setProdutoDoServico(rs.getString("produto_servico"));
		clienteServico.setCnpjDoServico(rs.getString("cnpj_servico"));
		clienteServico.setSaldoServico(rs.getInt("saldo_servico"));
		clienteServico.setObservacoesServico(rs.getString("observacoes_servico"));
		clienteServico.setLimiteMinimo(rs.getInt("limite_minimo"));
		clienteServico.setValorUnitario(rs.getDouble("valor_unitario"));

		return clienteServico;
	}

	@Override
	public void excluirPeloId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ClienteServico buscarPeloId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteServico> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteServico> buscarPeloNomeFantasia(String nomeFantasia) {
		// TODO Auto-generated method stub
		return null;
	}
}

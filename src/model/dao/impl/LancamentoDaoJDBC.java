package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LancamentoDao;
import model.entities.Lancamento;

public class LancamentoDaoJDBC implements LancamentoDao {

	private Connection conn;

	public LancamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Lancamento lancamento) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO lancamento (id_cliente_servico, data_do_lancamento, quantidade_do_pedido, saldo_anterior, saldo_atual,"
							+ " tipo_do_lancamento, observacoes_lancamento)" + " VALUES (?, ?, ? ,?, ?, ?, ?)");

			st.setInt(1, lancamento.getIdClienteServico());
			st.setDate(2, lancamento.getDataDoLancamento());
			st.setInt(3, lancamento.getQuantidadeDoPedido());
			st.setInt(4, lancamento.getSaldoAnterior());
			st.setInt(5, lancamento.getSaldoAtual());
			st.setString(6, lancamento.getTipoDoLancamento());
			st.setString(7, lancamento.getObservacoesLancamento());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Error ao fazer o lançamento! Nenhuma linha afetada no processo!");

			} else {

				new ClienteServicoDaoJDBC(conn).atualizarSaldo(lancamento.getSaldoAtual(),
						lancamento.getIdClienteServico());

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
	public void atualizar(Lancamento lancamento) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE lancamento SET id_cliente_servico = ?, data_do_lancamento = ?, quantidade_do_pedido = ?, saldo_anterior = ?, saldo_atual = ?,"
							+ " tipo_do_lancamento = ?, observacoes_lancamento = ? WHERE id_lancamento = ?");

			st.setInt(1, lancamento.getIdClienteServico());
			st.setDate(2, lancamento.getDataDoLancamento());
			st.setInt(3, lancamento.getQuantidadeDoPedido());
			st.setInt(4, lancamento.getSaldoAnterior());
			st.setInt(5, lancamento.getSaldoAtual());
			st.setString(6, lancamento.getTipoDoLancamento());
			st.setString(7, lancamento.getObservacoesLancamento());
			st.setInt(8, lancamento.getIdLancamento());

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
	public List<Lancamento> buscarTodos(Integer idClienteServico) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM lancamento WHERE id_cliente_servico = ? ORDER BY id_cliente_servico");

			st.setInt(1, idClienteServico);
			rs = st.executeQuery();

			List<Lancamento> lista = new ArrayList<>();

			if (rs.next()) {

				Lancamento lancamento = instantiateLancamento(rs);

				lista.add(lancamento);

			}

			conn.commit();

			return lista;

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

	private Lancamento instantiateLancamento(ResultSet rs) throws SQLException {

		Lancamento lancamento = new Lancamento();

		lancamento.setIdLancamento(rs.getInt("id_lancamento"));
		lancamento.setIdClienteServico(rs.getInt("id_cliente_servico"));
		lancamento.setDataDoLancamento(rs.getDate("data_do_lancamento"));
		lancamento.setQuantidadeDoPedido(rs.getInt("quantidade_do_pedido"));
		lancamento.setSaldoAnterior(rs.getInt("saldo_anterior"));
		lancamento.setSaldoAtual(rs.getInt("saldo_atual"));
		lancamento.setTipoDoLancamento(rs.getString("tipo_do_lancamento"));
		lancamento.setObservacoesLancamento(rs.getString("observacoes_lancamento"));

		return lancamento;
	}

	@Override
	public List<Lancamento> verLancamentos(Date dataInicial, Date dataFinal, int idClienteServico) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM lancamento WHERE data_do_lancamento between ? and ? and id_cliente_servico = ? order by id_lancamento");

			st.setDate(1, dataInicial);
			st.setDate(2, dataFinal);
			st.setInt(3, idClienteServico);
			rs = st.executeQuery();

			List<Lancamento> lista = new ArrayList<>();

			while (rs.next()) {

				Lancamento lancamento = instantiateLancamento(rs);
				lista.add(lancamento);

			}

			conn.commit();

			System.out.println(lista.toString());

			return lista;

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
	public void excluirPeloId(Integer id) {
		// TODO Auto-generated method stub

	}

}

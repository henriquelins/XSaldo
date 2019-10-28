package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClienteDao;
import model.entities.Cliente;
import model.entities.Contato;
import model.entities.Endereco;

public class ClienteDaoJDBC implements ClienteDao {

	private Connection conn;

	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Cliente cliente) {

		PreparedStatement st = null;

		ContatoDaoJDBC contatoDao = new ContatoDaoJDBC(conn);
		EnderecoDaoJDBC enderecoDao = new EnderecoDaoJDBC(conn);

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO cliente (nome_fantasia, razao_social, cnpj_cliente, inscricao_estadual, inscricao_municipal,"
							+ " data_cliente, detalhes_cliente, entrega_cliente, cod_vendedor, unidade_atendimento)"
							+ " VALUES (?, ?, ? ,?, ?, ?, ?, ? , ? , ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, cliente.getNomeFantasia());
			st.setString(2, cliente.getRazaoSocial());
			st.setString(3, cliente.getCnpjCliente());
			st.setString(4, cliente.getInscricaoEstadual());
			st.setString(5, cliente.getInscricaoMunicipal());
			st.setDate(6, cliente.getDataInicioCliente());
			st.setString(7, cliente.getDetalhesDoCliente());
			st.setBoolean(8, cliente.isEntregaNoCliente());
			st.setInt(9, cliente.getCod_vendedor());
			st.setString(10, cliente.getUnidadeDeAtendimento());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {

					cliente.setIdCliente(rs.getInt(1));
					contatoDao.inserir(cliente);
					enderecoDao.inserir(cliente);

				}

				DB.closeResultSet(rs);

			} else {

				throw new DbException("Error ao inserir o cliente! Nenhuma linha afetada no processo!");

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

	@Override
	public List<Cliente> buscarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Cliente> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM cliente as cl inner join contato as co on cl.id_cliente = co.id_cliente "
							+ "inner join endereco as en on cl.id_cliente = en.id_cliente order by cl.id_cliente");
			rs = st.executeQuery();

			while (rs.next()) {

				Cliente cliente = instantiateCliente(rs);
				list.add(cliente);

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
	public void atualizar(Cliente cliente) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE cliente SET nome_fantasia = ?, razao_social = ?, cnpj_cliente = ?, "
							+ "inscricao_estadual = ?, inscricao_municipal = ?, data_cliente = ?, detalhes_cliente = ?, "
							+ "entrega_cliente = ? , cod_vendedor = ?, unidade_atendimento = ?  WHERE id_cliente = ?");

			st.setString(1, cliente.getNomeFantasia());
			st.setString(2, cliente.getRazaoSocial());
			st.setString(3, cliente.getCnpjCliente());
			st.setString(4, cliente.getInscricaoEstadual());
			st.setString(5, cliente.getInscricaoMunicipal());
			st.setDate(6, cliente.getDataInicioCliente());
			st.setString(7, cliente.getDetalhesDoCliente());
			st.setBoolean(8, cliente.isEntregaNoCliente());
			st.setInt(9, cliente.getCod_vendedor());
			st.setString(10, cliente.getUnidadeDeAtendimento());
			st.setInt(11, cliente.getIdCliente());

			st.executeUpdate();

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

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {

		Cliente cliente = new Cliente();

		cliente.setIdCliente(rs.getInt("id_cliente"));
		cliente.setNomeFantasia(rs.getString("nome_fantasia"));
		cliente.setRazaoSocial(rs.getString("razao_social"));
		cliente.setCnpjCliente(rs.getString("cnpj_cliente"));
		cliente.setInscricaoEstadual(rs.getString("inscricao_estadual"));
		cliente.setInscricaoMunicipal(rs.getString("inscricao_municipal"));
		cliente.setDataInicioCliente(rs.getDate("data_cliente"));
		cliente.setDetalhesDoCliente(rs.getString("detalhes_cliente"));
		cliente.setEntregaNoCliente(rs.getBoolean("entrega_cliente"));
		cliente.setCod_vendedor(rs.getInt("cod_vendedor"));
		cliente.setUnidadeDeAtendimento(rs.getString("unidade_atendimento"));

		Contato contato = new Contato();

		contato.setIdContato(rs.getInt("id_contato"));
		contato.setContatoCliente(rs.getString("contato_cliente"));
		contato.setEmailCliente(rs.getString("email_cliente"));
		contato.setFoneCelular(rs.getString("fone_celular"));
		contato.setFoneFixo(rs.getString("fone_fixo"));

		Endereco endereco = new Endereco();

		endereco.setIdEndereco(rs.getInt("id_endereco"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setCidade(rs.getString("cidade"));
		endereco.setCep(rs.getString("cep"));
		endereco.setUf(rs.getString("uf"));

		cliente.setContato(contato);
		cliente.setEndereco(endereco);

		return cliente;

	}

	@Override
	public void excluirPeloId(Integer id) {
		

	}

	@Override
	public Cliente buscarPeloId(Integer id) {
		
		return null;
	}

	@Override
	public List<Cliente> buscarPeloNomeFantasia(String nomeFantasia) {
		
		return null;
	}

}

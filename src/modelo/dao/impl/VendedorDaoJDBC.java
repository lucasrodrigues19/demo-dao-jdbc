package modelo.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import db.DB;
import db.DbException;
import modelo.dao.VendedorDAO;
import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class VendedorDaoJDBC implements VendedorDAO {

	private Connection conn;
	private String sql;

	public VendedorDaoJDBC(Connection conn) {
		if (conn == null)
			throw new DbException("conexao nula");

		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		if (obj == null || obj.getDepartamento() == null)
			throw new DbException("O vendedor esta nulo");
		sql = "insert into vendedor VALUES (default,?,?,?,?,?)";
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			conn.setAutoCommit(false);

			st = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getDataNasc().getTime()));
			st.setDouble(4, obj.getBaseSalario());
			st.setInt(5, obj.getDepartamento().getId());
			int row = st.executeUpdate();
			System.out.println("Linhas inseridas: " + 1);
			if (row > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("ID: " + id);
				}
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Erro na transanção: " + e.getMessage());
			} catch (SQLException er) {
				throw new DbException("Erro no roolback: " + e.getMessage());
			}
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void atualizar(Vendedor obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletarPorID(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vendedor pesquisarPorID(Integer id) {
		if (id == null)
			throw new DbException("Id esta nulo");

		sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id where vendedor.id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		Vendedor vendedor = null;
		Departamento dpt = null;
		try {
			st = (PreparedStatement) conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.first()) {
				vendedor = new Vendedor();
				dpt = new Departamento();
				// departamento
				dpt.setId(rs.getInt("dptId"));
				dpt.setName(rs.getString("departamento.nome"));
				// vendedor
				vendedor.setId(rs.getInt("id"));
				vendedor.setNome(rs.getString("nome"));
				vendedor.setEmail(rs.getString("email"));
				vendedor.setDataNasc(rs.getDate("dataNasc"));
				vendedor.setBaseSalario(rs.getDouble("salarioBase"));
				vendedor.setDepartamento(dpt);
			}
			return vendedor;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> pesquisarTodos() {

		Statement st = null;
		ResultSet rs = null;
		List<Vendedor> result = new ArrayList<Vendedor>();
		Vendedor vendedor = null;
		Departamento dpt = null;
		try {
			sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				vendedor = new Vendedor();
				dpt = new Departamento();

				// departamento
				dpt.setId(rs.getInt("dptId"));
				dpt.setName(rs.getString("departamento.nome"));
				// vendedor
				vendedor.setId(rs.getInt("id"));
				vendedor.setNome(rs.getString("nome"));
				vendedor.setDataNasc(rs.getDate("dataNasc"));
				vendedor.setEmail(rs.getString("email"));
				vendedor.setBaseSalario(rs.getDouble("salarioBase"));
				vendedor.setDepartamento(dpt);
				// adiciona a lista
				result.add(vendedor);
			}
			return result;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> pesquisarPorDepartamento(Integer dptId) {
		if (dptId == null)
			throw new DbException("Id do departamento esta nulo");

		sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id where dptId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vendedor> result = new ArrayList<Vendedor>();
		Vendedor vendedor = null;
		Departamento dpt = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, dptId);
			rs = ps.executeQuery();
			while (rs.next()) {
				vendedor = new Vendedor();
				dpt = new Departamento();

				// departamento
				dpt.setId(rs.getInt("dptId"));
				dpt.setName(rs.getString("departamento.nome"));
				// vendedor
				vendedor.setId(rs.getInt("id"));
				vendedor.setNome(rs.getString("nome"));
				vendedor.setDataNasc(rs.getDate("dataNasc"));
				vendedor.setEmail(rs.getString("email"));
				vendedor.setBaseSalario(rs.getDouble("salarioBase"));
				vendedor.setDepartamento(dpt);
				// adiciona a lista
				result.add(vendedor);
			}
			return result;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

	}

}

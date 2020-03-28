package modelo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import db.DB;
import db.DbException;
import modelo.dao.DepartamentoDAO;
import modelo.entites.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDAO {

	private Connection conn;
	private String sql;

	public DepartamentoDaoJDBC(Connection conn) {
		if (conn == null)
			throw new DbException("Conexao nula");

		this.conn = conn;
	}

	@Override
	public void inserir(Departamento obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(Departamento obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletarPorID(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Departamento pesquisarPorID(Integer id) {
		if (id == null)
			throw new DbException("Id nulo");

		sql = "select * from departamento where id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = (PreparedStatement) conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.first())
				return instanciarDepartamentoRs(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;

	}

	@Override
	public List<Departamento> pesquisarTodos() {

		sql = "select * from departamento";
		Statement st = null;
		ResultSet rs = null;
		List<Departamento>result = new ArrayList<Departamento>();
		try {
			st =  conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				result.add(instanciarDepartamentoRs(rs));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	// instancia um Departamento
	private Departamento instanciarDepartamentoRs(ResultSet rs) throws SQLException {
		if (rs == null)
			throw new SQLException();

		Departamento dpt = new Departamento();
		dpt.setId(rs.getInt("id"));
		dpt.setName(rs.getString("nome"));
		return dpt;
	}
}

package modelo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import db.DB;
import db.DbException;
import modelo.dao.VendedorDAO;
import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class VendedorDaoJDBC implements VendedorDAO {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		if (conn == null)
			throw new DbException("conexao nula");

		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		// TODO Auto-generated method stub

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
		if(id == null)
			throw new DbException("Id esta nulo");
		
		String sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id where vendedor.id = ?";
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
				//vendedor
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
		// TODO Auto-generated method stub
		return null;
	}

}

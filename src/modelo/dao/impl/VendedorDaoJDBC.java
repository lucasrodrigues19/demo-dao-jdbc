package modelo.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.SQLError;

import db.DB;
import db.DbException;
import helper.DBHelper;
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
					obj.setId(id);
				}
			}else {
				throw new DbException("Nenhuma linha afetada: ");
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
		if (obj == null)
			throw new DbException("vededor nulo");

		PreparedStatement st = null;
		ResultSet rs = null;
		sql = "update vendedor set nome = ?,email = ?,dataNasc = ?,salarioBase = ?,dptId = ? where id = ?";
		try {
			conn.setAutoCommit(false);
			st = (PreparedStatement) conn.prepareStatement(sql);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getDataNasc().getTime()));
			st.setDouble(4, obj.getBaseSalario());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			int rows = st.executeUpdate();
			if(rows == 0)
				throw new DbException("Nenhuma linha afetada!");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				e.printStackTrace();
				System.out.println("Erro na transanção: " + e.getMessage());
			} catch (SQLException e1) {
				e.printStackTrace();
				System.out.println("Erro no roolback: " + e.getMessage());
			}
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public void deletarPorID(Integer id) {
		if(id == null)
			throw new DbException("O id esta nulo");
		sql = "delete from vendedor where id = ?";
		PreparedStatement st = null;
		try {
			conn.setAutoCommit(false);
			st = (PreparedStatement) conn.prepareStatement(sql);
			st.setInt(1, id);
		    int rows = st.executeUpdate();
		    if(rows == 0)
				throw new DbException("Nenhuma linha afetada!");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				e.printStackTrace();
				throw new DbException("Erro na transanção");
			}catch(SQLException e1) {
				e.printStackTrace();
				throw new DbException("Erro no rollback");
			}finally {
				DB.closeStatement(st);
			}
		}
	}

	@Override
	public Vendedor pesquisarPorID(Integer id) {
		if (id == null)
			throw new DbException("Id esta nulo");

		sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id where vendedor.id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		Vendedor vendedor = null;
		try {
			st = (PreparedStatement) conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.first()) {
				vendedor = DBHelper.instanciarVendedorRs(rs, DBHelper.instanciarDepartamentoRs(rs));
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
		try {
			sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				vendedor = DBHelper.instanciarVendedorRs(rs, DBHelper.instanciarDepartamentoRs(rs));
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
	public List<Vendedor> pesquisarPorDepartamento(Departamento obj) {
		if (obj == null)
			throw new DbException("Id do departamento esta nulo");

		sql = "select vendedor.* , departamento.nome from vendedor INNER JOIN departamento on vendedor.dptId = departamento.id where dptId = ? ORDER BY  vendedor.nome";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vendedor> result = new ArrayList<Vendedor>();
		Map<Integer, Departamento>dptMap = new HashMap<Integer, Departamento>();
		Vendedor vendedor = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, obj.getId());
			rs = ps.executeQuery();
			Departamento dpt = null;
			while (rs.next()) {
				// departamento 1 --- N vendedores.
				// O vendedor aponta apenas para um objeto departamento
				if(dptMap != null && dptMap.size() > 0) {
					if(!dptMap.containsKey(rs.getInt("dptId"))){
					 dpt = dptMap.get(rs.getInt("dptId"));
					}
				}else {
					dpt = DBHelper.instanciarDepartamentoRs(rs);
					dptMap.put(rs.getInt("dptId"), DBHelper.instanciarDepartamento(dpt));
				}
				
				vendedor = DBHelper.instanciarVendedorRs(rs, DBHelper.instanciarDepartamento(dpt));
				result.add(vendedor);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

	}

}

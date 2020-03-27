package modelo.dao.impl;

import java.sql.Connection;
import java.util.List;

import modelo.dao.DepartamentoDAO;
import modelo.entites.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDAO {

	private Connection conn;
	public DepartamentoDaoJDBC(Connection conn) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Departamento> pesquisarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}

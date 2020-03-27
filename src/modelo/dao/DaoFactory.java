package modelo.dao;

import java.sql.Connection;

import modelo.dao.impl.DepartamentoDaoJDBC;
import modelo.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

	public static VendedorDAO criarVendedorDAO(Connection conn) {
		return new VendedorDaoJDBC(conn);
	}
	public static DepartamentoDAO criarDepartamentoDAO(Connection conn) {
		return new DepartamentoDaoJDBC(conn);
	}
}

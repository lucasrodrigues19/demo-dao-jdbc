package modelo.dao;

import modelo.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

	public static VendedorDAO criarVendedorDAO() {
		return new VendedorDaoJDBC();
	}
}

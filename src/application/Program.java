package application;

import java.util.Date;

import db.DB;
import modelo.dao.DaoFactory;
import modelo.dao.VendedorDAO;
import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class Program {

	public static void main(String[] args) {
//		Departamento ob = new Departamento(1, "Livro");
//		System.out.println(ob);
//		Vendedor ven = new Vendedor(1,"Lucas","lucas@gmail.com",new Date(),100.0,ob);
//		System.out.println(ven);
		
		VendedorDAO vendedorDAO = DaoFactory.criarVendedorDAO(DB.getConnection());
		Vendedor ven = vendedorDAO.pesquisarPorID(9);
		System.out.println(ven);
		
	}
}

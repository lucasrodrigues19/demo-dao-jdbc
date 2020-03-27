package application;

import java.text.SimpleDateFormat;
import java.util.List;

import db.DB;
import modelo.dao.DaoFactory;
import modelo.dao.VendedorDAO;
import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class Program {
static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static void main(String[] args) {
//		Departamento ob = new Departamento(1, "Livro");
//		System.out.println(ob);
//		Vendedor ven = new Vendedor(1,"Lucas","lucas@gmail.com",new Date(),100.0,ob);
//		System.out.println(ven);
		try {
		VendedorDAO vendedorDAO = DaoFactory.criarVendedorDAO(DB.getConnection());
		Vendedor ven = vendedorDAO.pesquisarPorID(2);
//		List<Vendedor>vendedores = vendedorDAO.pesquisarTodos();
//		vendedores.forEach(v->System.out.println(v));
//		System.out.println(ven);
//		Vendedor ve = new Vendedor(2, "Marcos Junior", "marcos@gmail.com",sdf.parse("10/04/1999") , 3800.0, ven.getDepartamento());
//		vendedorDAO.inserir(ve);
		vendedorDAO.atualizar(ven);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

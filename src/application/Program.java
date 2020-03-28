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
		try {
		VendedorDAO vendedorDAO = DaoFactory.criarVendedorDAO(DB.getConnection());
		System.out.println("== pesquisar por id ==");
		Vendedor ven = vendedorDAO.pesquisarPorID(6);
		System.out.println(ven);
		System.out.println("== pesquisarTodos ==");
		List<Vendedor>vendedores = vendedorDAO.pesquisarTodos();
		vendedores.forEach(v->System.out.println(v));
//		Vendedor ve = new Vendedor(2, "Marcos Junior", "marcos@gmail.com",sdf.parse("10/04/1999") , 3800.0, ven.getDepartamento());
//		vendedorDAO.inserir(ve);
		ven.setNome("Joao");
		ven.setEmail("joao@gmail.com");
		vendedorDAO.atualizar(ven);
		System.out.println("== pesquisarPorDepartamento ==");
		List<Vendedor> vdpt = vendedorDAO.pesquisarPorDepartamento(ven.getDepartamento());
		vdpt.forEach(v -> System.out.println(v));
//		vendedorDAO.deletarPorID(10);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

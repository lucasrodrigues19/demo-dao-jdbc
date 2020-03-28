package application;

import java.util.List;

import db.DB;
import modelo.dao.DaoFactory;
import modelo.dao.DepartamentoDAO;
import modelo.entites.Departamento;

public class Program2 {

	/**
	 * Testar o departamento
	 * @param args
	 */
	public static void main(String[] args) {
		DepartamentoDAO dptDAO = DaoFactory.criarDepartamentoDAO(DB.getConnection());
		
		System.out.println("== pesquisarPorId ==");
		Departamento dptId = dptDAO.pesquisarPorID(6);
		System.out.println(dptId);
		System.out.println("== pesquisarTodos ==");
		List<Departamento>dpTodos = dptDAO.pesquisarTodos();
		dpTodos.forEach(d->System.out.println(d));
//		System.out.println("== inserir ==");
//		Departamento dp = new Departamento(null, "Teste02");
//		dptDAO.inserir(dp);
//		System.out.println("Id inserido: "+dp.getId());
		System.out.println("== atualizar ==");
		Departamento dpA = dptId;
		dpA.setName("Santo");
		dptDAO.atualizar(dpA);
		System.out.println(dpA);
		System.out.println("== deletarId ==");
		dptDAO.deletarPorID(9);
	}
}

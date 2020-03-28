package application;

import java.util.List;

import db.DB;
import modelo.dao.DaoFactory;
import modelo.dao.DepartamentoDAO;
import modelo.entites.Departamento;

public class Program2 {

	public static void main(String[] args) {
		DepartamentoDAO dptDAO = DaoFactory.criarDepartamentoDAO(DB.getConnection());
		
		System.out.println("== pesquisarPorId ==");
		Departamento dptId = dptDAO.pesquisarPorID(1);
		System.out.println(dptId);
		System.out.println("== pesquisarTodos ==");
		List<Departamento>dpTodos = dptDAO.pesquisarTodos();
		dpTodos.forEach(d->System.out.println(d));
	}
}

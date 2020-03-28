package helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.SQLError;

import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class DBHelper {

	
		public static Vendedor instanciarVendedorRs(ResultSet rs, Departamento dpt)throws SQLException {
			if(rs == null || dpt == null) {
				
			}
			Vendedor vendedor = new Vendedor();
			vendedor.setId(rs.getInt("id"));
			vendedor.setNome(rs.getString("nome"));
			vendedor.setDataNasc(rs.getDate("dataNasc"));
			vendedor.setEmail(rs.getString("email"));
			vendedor.setBaseSalario(rs.getDouble("salarioBase"));
			vendedor.setDepartamento(dpt);
			return vendedor;
	}
		public static Vendedor instanciarVendedor(Vendedor vendedor) {
			if(vendedor == null)
				return new Vendedor();
			
			return vendedor;
		}
		public static Departamento instanciarDepartamentoRs(ResultSet rs)throws SQLException {
			Departamento dpt = new Departamento();
			dpt.setId(rs.getInt("dptId"));
			dpt.setName(rs.getString("departamento.nome"));
			return dpt;
		}
		public static Departamento instanciarDepartamento(Departamento dpt)throws SQLException {
			if(dpt == null)
				return new Departamento();
			
			return dpt;
		}
}

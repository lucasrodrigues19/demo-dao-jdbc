package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.entites.Departamento;
import modelo.entites.Vendedor;

public class DBHelper {

	
		
		public static Vendedor instanciarVendedor(Vendedor vendedor) {
			if(vendedor == null)
				return new Vendedor();
			
			return vendedor;
		}
		
		public static Departamento instanciarDepartamento(Departamento dpt)throws SQLException {
			if(dpt == null)
				return new Departamento();
			
			return dpt;
		}
}

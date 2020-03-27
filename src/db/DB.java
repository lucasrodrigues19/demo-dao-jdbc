package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Connection conn = null;

	/**
	 * Metodo para fazer uma conexao com o banco 
	 * @return Connection
	 */
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException("Erro ao conectar: "+e.getMessage());
			}
		}
		return conn;
	}

	/**
	 * Fecha a conexao com o banco 
	 */
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}	
	}

	/**
	 * Metodo para ler os dados de um aqrquivo .properties
	 * @return
	 */
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;

		} catch (IOException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
	}
	/**
	 * Fecha o objeto Statement
	 * @param st Statment
	 */
	public static void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	/**
	 * Fecha o objeto ResultSet
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs	 != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}

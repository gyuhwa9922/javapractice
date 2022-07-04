package Connecter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements DBConnector{
	private final String ADDRESS = "jdbc:mysql://localhost:3306/board?useUnicode=true&characterEncoding=utf8&useSSL=false";
	private final String USERNAME = "root";
	private final String PASSWORD = "@@@@";
	
	@Override
	public Connection makeConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					ADDRESS, USERNAME,
					PASSWORD);
			
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
}

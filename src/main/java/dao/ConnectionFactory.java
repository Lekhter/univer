package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String DB_HOST = "localhost:3306";

	private static final String DB_DATABASE = "doc";

	private static final String USER_NAME = "root";

	private static final String PASSWORD = "root";

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://" + DB_HOST + "/" + DB_DATABASE;
		return DriverManager.getConnection(url, USER_NAME, PASSWORD);
	}
}

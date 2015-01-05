package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Status;
import beans.User;

public class UserDAO {

	public static void main(String[] args) throws SQLException {
		User u = new UserDAO().authenticateUser("ADMIN", "ADMIN");
		System.out.println(u.getName());
	}

	public User authenticateUser(String name, String password)
			throws SQLException {
		User result = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = connection
				.prepareStatement("Select * from users where name = ? and password = ?");
		stmt.setString(1, name);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			result = new User();
			result.setName(rs.getString("name"));
			result.setStatus(readStatus(rs.getInt("status_id")));
		}
		stmt.close();
		connection.close();

		return result;
	}

	private Status readStatus(int id) throws SQLException {
		Status status = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement stmt = connection
				.prepareStatement("Select * from status where status_id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			status = new Status();
			status.setStatusId(id);
			status.setName(rs.getString("status_name"));
		}

		return status;
	}

}

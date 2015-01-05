import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;

import org.junit.Test;

import beans.User;
import dao.ConnectionFactory;
import dao.UserDAO;

public class UserDAOTest {
	@Test
	public void testAuthenticateUser() throws SQLException {
		UserDAO dao = new UserDAO();
		User u = dao.authenticateUser("ADMIN", "ADMIN");
		Assert.assertNotNull(u);
		Assert.assertEquals("ADMIN", u.getName());
		Assert.assertEquals("ADMIN", u.getStatus().getName());
	}

	@Test
	public void testNotAuthenticateUser() throws SQLException {
		UserDAO dao = new UserDAO();
		User u = dao.authenticateUser("ADMIN1", "ADMIN1");
		Assert.assertNull(u);
	}

}

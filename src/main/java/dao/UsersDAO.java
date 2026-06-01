package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;
import model.User;

public class UsersDAO {
	private final String JDBC_URL = "jdbc:sqlserver://localhost\\\\\\\\SQLEXPRESS:61371;databaseName=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

	public User findByLogin(Login login) {
		User user = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {

			String sql = "SELECT USER_ID, PASSWORD_HASH FROM USERS WHERE USER_ID = ? AND PASSWORD_HASH= ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUserId());
			pStmt.setString(2, login.getPasswordHash());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				String userId = rs.getString("USER_ID");
				String passwordHash = rs.getString("PASSWORD_HASH");
				user = new User(userId, passwordHash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

}

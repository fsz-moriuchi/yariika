package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import model.UserLogin;
import util.DButil;

public class UsersDAO {
	public User findByLogin(UserLogin login){
		User user = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public boolean registerUser(User user){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO USERS(USER_ID, PASSWORD_HASH) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getPasswordHash());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

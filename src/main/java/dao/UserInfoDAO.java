package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.UserInfo;
import util.DButil;

public class UserInfoDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_USER_INFO = "INSERT INTO UserInfo (USER_ID, USER_NAME, USER_GENDER, USER_BIRTHDAY, USER_TEL, USER_MAIL, USER_ADDRESS) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM UserInfo WHERE USER_ID=?";
	private static final String SQL_UPDATE_USER_INFO = "UPDATE UserInfo SET USER_NAME=?, USER_GENDER=?, USER_BIRTHDAY=?, USER_TEL=?, USER_MAIL=?, USER_ADDRESS=? WHERE USER_INFO_ID=?";

	// 個人情報の入力・セッションからのuserIdの取得
	public boolean insert(UserInfo userInfo) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_USER_INFO)) {

			bindInsertUserInfo(stmt, userInfo);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 個人情報一覧取得
	public UserInfo findByUserId(String userId) {
		UserInfo userInfo = null;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_USER_ID)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					userInfo = toUserInfo(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return userInfo;
	}

	// 個人情報更新(修正)
	public boolean updateInfo(UserInfo userInfo) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_USER_INFO)) {

			bindUpdateUserInfo(stmt, userInfo);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindInsertUserInfo(PreparedStatement stmt, UserInfo userInfo) throws Exception {
		stmt.setString(1, userInfo.getUserId());
		stmt.setString(2, userInfo.getUserName());
		stmt.setString(3, userInfo.getUserGender());
		stmt.setDate(4, userInfo.getUserBirthday());
		stmt.setString(5, userInfo.getUserTel());
		stmt.setString(6, userInfo.getUserMail());
		stmt.setString(7, userInfo.getUserAddress());
	}

	private void bindUpdateUserInfo(PreparedStatement stmt, UserInfo userInfo) throws Exception {
		stmt.setString(1, userInfo.getUserName());
		stmt.setString(2, userInfo.getUserGender());
		stmt.setDate(3, userInfo.getUserBirthday());
		stmt.setString(4, userInfo.getUserTel());
		stmt.setString(5, userInfo.getUserMail());
		stmt.setString(6, userInfo.getUserAddress());
		stmt.setInt(7, userInfo.getUserInfoId());
	}

	private UserInfo toUserInfo(ResultSet rs) throws Exception {
		int userInfoId = rs.getInt("USER_INFO_ID");
		String dbUserId = rs.getString("USER_ID");
		String userName = rs.getString("USER_NAME");
		String userGender = rs.getString("USER_GENDER");
		Date userBirthday = rs.getDate("USER_BIRTHDAY");
		String userTel = rs.getString("USER_TEL");
		String userMail = rs.getString("USER_MAIL");
		String userAddress = rs.getString("USER_ADDRESS");

		return new UserInfo(userInfoId, dbUserId, userName, userGender, userBirthday, userTel, userMail, userAddress);
	}
}
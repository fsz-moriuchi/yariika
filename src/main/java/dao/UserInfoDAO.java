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

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public boolean insert(UserInfo userInfo) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_INFO)) {

			bindInsertUserInfo(statement, userInfo);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserInfo findByUserId(String userId) {
		UserInfo userInfo = null;

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

			statement.setString(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					userInfo = toUserInfo(resultSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return userInfo;
	}

	public boolean updateInfo(UserInfo userInfo) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_INFO)) {

			bindUpdateUserInfo(statement, userInfo);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void bindInsertUserInfo(PreparedStatement statement, UserInfo userInfo) throws Exception {
		statement.setString(1, userInfo.getUserId());
		statement.setString(2, userInfo.getUserName());
		statement.setString(3, userInfo.getUserGender());
		statement.setDate(4, userInfo.getUserBirthday());
		statement.setString(5, userInfo.getUserTel());
		statement.setString(6, userInfo.getUserMail());
		statement.setString(7, userInfo.getUserAddress());
	}

	private void bindUpdateUserInfo(PreparedStatement statement, UserInfo userInfo) throws Exception {
		statement.setString(1, userInfo.getUserName());
		statement.setString(2, userInfo.getUserGender());
		statement.setDate(3, userInfo.getUserBirthday());
		statement.setString(4, userInfo.getUserTel());
		statement.setString(5, userInfo.getUserMail());
		statement.setString(6, userInfo.getUserAddress());
		statement.setInt(7, userInfo.getUserInfoId());
	}

	private UserInfo toUserInfo(ResultSet resultSet) throws Exception {
		int userInfoId = resultSet.getInt("USER_INFO_ID");
		String dbUserId = resultSet.getString("USER_ID");
		String userName = resultSet.getString("USER_NAME");
		String userGender = resultSet.getString("USER_GENDER");
		Date userBirthday = resultSet.getDate("USER_BIRTHDAY");
		String userTel = resultSet.getString("USER_TEL");
		String userMail = resultSet.getString("USER_MAIL");
		String userAddress = resultSet.getString("USER_ADDRESS");

		return new UserInfo(userInfoId, dbUserId, userName, userGender, userBirthday, userTel, userMail, userAddress);
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.UserLogin;
import model.UserSurvey;
import util.DButil;

public class UsersDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_BY_LOGIN = "SELECT USER_ID, PASSWORD_HASH FROM USERS WHERE USER_ID = ? AND PASSWORD_HASH = ?";
	private static final String SQL_REGISTER_USER = "INSERT INTO USERS(USER_ID, PASSWORD_HASH) VALUES(?, ?)";
	private static final String SQL_CREATE_USER_SURVEY = "INSERT INTO UserSurvey(USER_ID,QuestionID,SurveyChoiceID) VALUES(?,?,?)";
	private static final String SQL_UPDATE_USER_SURVEY = "UPDATE UserSurvey SET SurveyChoiceID = ? WHERE USER_ID = ? AND QuestionID = ?";
	private static final String SQL_INSERT_USER_SURVEY = "INSERT INTO UserSurvey (USER_ID, QuestionID, SurveyChoiceID) VALUES(?, ?, ?)";
	private static final String SQL_SHOW_USER_SURVEY = "SELECT * FROM UserSurvey WHERE USER_ID = ?";
	private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE USERS SET PASSWORD_HASH = ? WHERE USER_ID = ?";

	public User findByLogin(UserLogin login) {
		User user = null;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_LOGIN)) {

			stmt.setString(1, login.getUserId());
			stmt.setString(2, login.getPasswordHash());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User(rs.getString("USER_ID"), rs.getString("PASSWORD_HASH"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return user;
	}

	public boolean registerUser(User user) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_REGISTER_USER)) {

			bindRegisterUser(stmt, user);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 新規ユーザーアンケート
	public boolean createUserSurvey(UserSurvey userSurvey) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_USER_SURVEY)) {

			bindCreateUserSurvey(stmt, userSurvey);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 修正ユーザーアンケート
	public boolean updateUserSurvey(String userId, int questionID, int surveyChoiceID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			if (updateExistingUserSurvey(conn, userId, questionID, surveyChoiceID)) {
				return true;
			}
			return insertUserSurvey(conn, userId, questionID, surveyChoiceID);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// show ユーザーアンケート
	public List<UserSurvey> showUserSurvey(String userId) {
		List<UserSurvey> userSurveyList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_USER_SURVEY)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					userSurveyList.add(toUserSurvey(userId, rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userSurveyList;
	}

	// パスワード変更
	public boolean updateUserPassword(User user) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {

			bindUpdateUserPassword(stmt, user);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindRegisterUser(PreparedStatement stmt, User user) throws Exception {
		stmt.setString(1, user.getUserId());
		stmt.setString(2, user.getPasswordHash());
	}

	private void bindCreateUserSurvey(PreparedStatement stmt, UserSurvey userSurvey) throws Exception {
		stmt.setString(1, userSurvey.getUserId());
		stmt.setInt(2, userSurvey.getQuestionID());
		stmt.setInt(3, userSurvey.getSurveyChoiceID());
	}

	private boolean updateExistingUserSurvey(Connection conn, String userId, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_USER_SURVEY)) {
			stmt.setInt(1, surveyChoiceID);
			stmt.setString(2, userId);
			stmt.setInt(3, questionID);
			return stmt.executeUpdate() == 1;
		}
	}

	private boolean insertUserSurvey(Connection conn, String userId, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_USER_SURVEY)) {
			stmt.setString(1, userId);
			stmt.setInt(2, questionID);
			stmt.setInt(3, surveyChoiceID);
			return stmt.executeUpdate() == 1;
		}
	}

	private UserSurvey toUserSurvey(String userId, ResultSet rs) throws Exception {
		int questionID = rs.getInt("QuestionID");
		int surveyChoiceID = rs.getInt("SurveyChoiceID");
		return new UserSurvey(userId, questionID, surveyChoiceID);
	}

	private void bindUpdateUserPassword(PreparedStatement stmt, User user) throws Exception {
		stmt.setString(1, user.getPasswordHash());
		stmt.setString(2, user.getUserId());
	}
}
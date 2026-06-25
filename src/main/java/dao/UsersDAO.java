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
	private static final String SQL_UPDATE_USER_SURVEY = "UPDATE UserSurvey SET SurveyChoiceID = ? WHERE USER_ID = ? AND QuestionID = ?";
	private static final String SQL_INSERT_USER_SURVEY = "INSERT INTO UserSurvey (USER_ID, QuestionID, SurveyChoiceID) VALUES(?, ?, ?)";
	private static final String SQL_SHOW_USER_SURVEY = "SELECT * FROM UserSurvey WHERE USER_ID = ?";
	private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE USERS SET PASSWORD_HASH = ? WHERE USER_ID = ?";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public User findByLogin(UserLogin login) {
		User user = null;

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN)) {

			statement.setString(1, login.getUserId());
			statement.setString(2, login.getPasswordHash());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					user = new User(resultSet.getString("USER_ID"), resultSet.getString("PASSWORD_HASH"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return user;
	}

	public boolean registerUser(User user) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_REGISTER_USER)) {

			bindRegisterUser(statement, user);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createUserSurvey(UserSurvey userSurvey) {
		return insertUserSurvey(userSurvey);
	}

	public boolean updateUserSurvey(String userId, int questionID, int surveyChoiceID) {
		try (Connection connection = DButil.getConnection()) {
			if (updateExistingUserSurvey(connection, userId, questionID, surveyChoiceID)) {
				return true;
			}
			return insertUserSurvey(connection, userId, questionID, surveyChoiceID);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<UserSurvey> showUserSurvey(String userId) {
		List<UserSurvey> userSurveyList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_USER_SURVEY)) {

			statement.setString(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					userSurveyList.add(toUserSurvey(userId, resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userSurveyList;
	}

	public boolean updateUserPassword(User user) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {

			bindUpdateUserPassword(statement, user);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void bindRegisterUser(PreparedStatement statement, User user) throws Exception {
		statement.setString(1, user.getUserId());
		statement.setString(2, user.getPasswordHash());
	}

	private boolean insertUserSurvey(UserSurvey userSurvey) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_SURVEY)) {

			bindInsertUserSurvey(statement, userSurvey);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean insertUserSurvey(Connection connection, String userId, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_SURVEY)) {
			statement.setString(1, userId);
			statement.setInt(2, questionID);
			statement.setInt(3, surveyChoiceID);
			return statement.executeUpdate() == 1;
		}
	}

	private void bindInsertUserSurvey(PreparedStatement statement, UserSurvey userSurvey) throws Exception {
		statement.setString(1, userSurvey.getUserId());
		statement.setInt(2, userSurvey.getQuestionID());
		statement.setInt(3, userSurvey.getSurveyChoiceID());
	}

	private boolean updateExistingUserSurvey(Connection connection, String userId, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_SURVEY)) {
			statement.setInt(1, surveyChoiceID);
			statement.setString(2, userId);
			statement.setInt(3, questionID);
			return statement.executeUpdate() == 1;
		}
	}

	private UserSurvey toUserSurvey(String userId, ResultSet resultSet) throws Exception {
		int questionID = resultSet.getInt("QuestionID");
		int surveyChoiceID = resultSet.getInt("SurveyChoiceID");
		return new UserSurvey(userId, questionID, surveyChoiceID);
	}

	private void bindUpdateUserPassword(PreparedStatement statement, User user) throws Exception {
		statement.setString(1, user.getPasswordHash());
		statement.setString(2, user.getUserId());
	}
}
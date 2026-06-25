package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Question;
import util.DButil;

public class QuestionSurveyDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_ALL_QUESTIONS = "SELECT QuestionID, userQuestion, petQuestion FROM QuestionSurvey";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public List<Question> findAllQuestion() {
		List<Question> questions = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_QUESTIONS);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				questions.add(mapQuestion(resultSet));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return questions;
	}

	private Question mapQuestion(ResultSet resultSet) throws Exception {
		return new Question(
				resultSet.getInt("QuestionID"),
				resultSet.getString("userQuestion"),
				resultSet.getString("petQuestion"));
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.QuizAnswer;
import util.DButil;

public class QuizAnswerDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_QUIZ_ANSWER = "INSERT INTO QuizAnswer (USER_ID, QUIZ_ID, USER_ANSWER, QUIZ_SESSION_ID) VALUES (?, ?, ?, ?)";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public void insert(QuizAnswer quizAnswer) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_QUIZ_ANSWER)) {

			bindQuizAnswer(statement, quizAnswer);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindQuizAnswer(PreparedStatement statement, QuizAnswer quizAnswer) throws Exception {
		statement.setString(1, quizAnswer.getUserId());
		statement.setInt(2, quizAnswer.getQuizId());
		statement.setInt(3, quizAnswer.getUserAnswer());
		statement.setString(4, quizAnswer.getQuizSessionId());
	}
}
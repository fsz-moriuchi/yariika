package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.QuizResult;
import util.DButil;

public class QuizResultDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_BY_USER_ID = "SELECT q.QUIZ_ID, q.QUESTION, q.CHOICE1, q.CHOICE2, "
			+ "q.CHOICE3, q.CHOICE4, q.ANSWER, a.USER_ANSWER "
			+ "FROM QuizAnswer a "
			+ "JOIN PetQuiz q ON a.QUIZ_ID = q.QUIZ_ID "
			+ "WHERE a.USER_ID = ? AND a.QUIZ_SESSION_ID = ? "
			+ "ORDER BY q.QUIZ_ID DESC";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public List<QuizResult> findByUserId(String userId, String quizSessionId) {
		List<QuizResult> quizResults = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

			bindSearchCondition(statement, userId, quizSessionId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					quizResults.add(mapQuizResult(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return quizResults;
	}

	private void bindSearchCondition(PreparedStatement statement, String userId, String quizSessionId)
			throws Exception {
		statement.setString(1, userId);
		statement.setString(2, quizSessionId);
	}

	private QuizResult mapQuizResult(ResultSet resultSet) throws Exception {
		int quizId = resultSet.getInt("QUIZ_ID");
		String question = resultSet.getString("QUESTION");
		String choice1 = resultSet.getString("CHOICE1");
		String choice2 = resultSet.getString("CHOICE2");
		String choice3 = resultSet.getString("CHOICE3");
		String choice4 = resultSet.getString("CHOICE4");
		int answer = resultSet.getInt("ANSWER");
		int userAnswer = resultSet.getInt("USER_ANSWER");

		return new QuizResult(quizId, question, choice1, choice2, choice3, choice4, answer, userAnswer);
	}
}
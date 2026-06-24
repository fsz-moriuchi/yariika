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

	public List<QuizResult> findByUserId(String userId, String quizSessionId) {
		List<QuizResult> resultList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_USER_ID)) {

			bindFindByUserId(stmt, userId, quizSessionId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					resultList.add(toQuizResult(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return resultList;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindFindByUserId(PreparedStatement stmt, String userId, String quizSessionId) throws Exception {
		stmt.setString(1, userId);
		stmt.setString(2, quizSessionId);
	}

	private QuizResult toQuizResult(ResultSet rs) throws Exception {
		int quizId = rs.getInt("QUIZ_ID");
		String question = rs.getString("QUESTION");
		String choice1 = rs.getString("CHOICE1");
		String choice2 = rs.getString("CHOICE2");
		String choice3 = rs.getString("CHOICE3");
		String choice4 = rs.getString("CHOICE4");
		int answer = rs.getInt("ANSWER");
		int userAnswer = rs.getInt("USER_ANSWER");

		return new QuizResult(quizId, question, choice1, choice2, choice3, choice4, answer, userAnswer);
	}
}
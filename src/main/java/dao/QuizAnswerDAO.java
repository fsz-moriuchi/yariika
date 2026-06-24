package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.QuizAnswer;
import util.DButil;

public class QuizAnswerDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_QUIZ_ANSWER = "INSERT INTO QuizAnswer (USER_ID, QUIZ_ID, USER_ANSWER, QUIZ_SESSION_ID) VALUES (?, ?, ?, ?)";

	// userIdをインサート
	public void insert(QuizAnswer answer) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_QUIZ_ANSWER)) {

			bindQuizAnswer(stmt, answer);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindQuizAnswer(PreparedStatement stmt, QuizAnswer answer) throws Exception {
		stmt.setString(1, answer.getUserId());
		stmt.setInt(2, answer.getQuizId());
		stmt.setInt(3, answer.getUserAnswer());
		stmt.setString(4, answer.getQuizSessionId());
	}
}
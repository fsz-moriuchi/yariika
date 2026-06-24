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
	private static final String SQL_FIND_ALL_QUESTIONS = "SELECT * FROM QuestionSurvey";

	public List<Question> findAllQuestion() {
		List<Question> questionList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_ALL_QUESTIONS);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				questionList.add(toQuestion(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return questionList;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private Question toQuestion(ResultSet rs) throws Exception {
		int questionId = rs.getInt("QuestionID");
		String userQuestion = rs.getString("userQuestion");
		String petQuestion = rs.getString("petQuestion");
		return new Question(questionId, userQuestion, petQuestion);
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.PetQuiz;
import util.DButil;

public class PetQuizDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_BY_CATEGORY = "SELECT * FROM PetQuiz WHERE CATEGORY_ID = ?";

	// クイズ一覧取得
	public List<PetQuiz> findByCategory(int categoryId) {
		List<PetQuiz> quizList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CATEGORY)) {

			stmt.setInt(1, categoryId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					quizList.add(toPetQuiz(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return quizList;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private PetQuiz toPetQuiz(ResultSet rs) throws Exception {
		int quizId = rs.getInt("QUIZ_ID");
		String question = rs.getString("QUESTION");
		String choice1 = rs.getString("CHOICE1");
		String choice2 = rs.getString("CHOICE2");
		String choice3 = rs.getString("CHOICE3");
		String choice4 = rs.getString("CHOICE4");
		int answer = rs.getInt("ANSWER");
		int category = rs.getInt("CATEGORY_ID");

		return new PetQuiz(quizId, question, choice1, choice2, choice3, choice4, answer, category);
	}
}
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

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public List<PetQuiz> findByCategory(int categoryId) {
		List<PetQuiz> quizList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_CATEGORY)) {

			statement.setInt(1, categoryId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					quizList.add(toPetQuiz(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return quizList;
	}

	private PetQuiz toPetQuiz(ResultSet resultSet) throws Exception {
		int quizId = resultSet.getInt("QUIZ_ID");
		String question = resultSet.getString("QUESTION");
		String choice1 = resultSet.getString("CHOICE1");
		String choice2 = resultSet.getString("CHOICE2");
		String choice3 = resultSet.getString("CHOICE3");
		String choice4 = resultSet.getString("CHOICE4");
		int answer = resultSet.getInt("ANSWER");
		int categoryId = resultSet.getInt("CATEGORY_ID");

		return new PetQuiz(quizId, question, choice1, choice2, choice3, choice4, answer, categoryId);
	}
}
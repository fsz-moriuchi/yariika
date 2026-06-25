package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Choice;
import util.DButil;

public class SurveyChoiceDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_ALL_CHOICES = "SELECT * FROM SurveyChoice";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public List<Choice> findAllChoices() {
		List<Choice> choices = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CHOICES);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				choices.add(mapChoice(resultSet));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return choices;
	}

	private Choice mapChoice(ResultSet resultSet) throws Exception {
		int surveyChoiceId = resultSet.getInt("SurveyChoiceID");
		int questionId = resultSet.getInt("QuestionID");
		String choiceText = resultSet.getString("Choice");
		return new Choice(surveyChoiceId, questionId, choiceText);
	}
}
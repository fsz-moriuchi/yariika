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

	public List<Choice> findAllChoices() {
		List<Choice> choiceList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_ALL_CHOICES);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				choiceList.add(toChoice(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return choiceList;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private Choice toChoice(ResultSet rs) throws Exception {
		int surveyChoiceId = rs.getInt("SurveyChoiceID");
		int questionId = rs.getInt("QuestionID");
		String choiceText = rs.getString("Choice");
		return new Choice(surveyChoiceId, questionId, choiceText);
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Choice;
import util.DButil;

public class SurveyChoiceDAO {

	public List<Choice> findAllChoices() {
		List<Choice> allChoiceList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT * FROM SurveyChoice";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int SurveyChoiceID = rs.getInt("SurveyChoiceID");
				int QuestionID = rs.getInt("QuestionID");
				String Choice = rs.getString("Choice");
				Choice choice = new Choice(SurveyChoiceID, QuestionID, Choice);
				allChoiceList.add(choice);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return allChoiceList;
	}

}

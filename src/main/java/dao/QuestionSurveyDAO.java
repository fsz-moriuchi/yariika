package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Question;
import util.DButil;

public class QuestionSurveyDAO {

	public List<Question> findAllQuestion() {
		List<Question> questionSurveyList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT * FROM QuestionSurvey";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int QuestionID = rs.getInt("QuestionID");
				String userQuestion = rs.getString("userQuestion");
				String petQuestion = rs.getString("petQuestion");
				Question question = new Question(QuestionID, userQuestion, petQuestion);
				questionSurveyList.add(question);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionSurveyList;
	}
	//ペット用質問だけを取得する(1~10)
	public List<Question> findPetQuestions() {
		List<Question> petQuestionList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}


		try (Connection conn = DButil.getConnection()){
			String sql = "SELECT * FROM QuestionSurvey WHERE QuestionID BETWEEN 1 AND 10";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int questionID = rs.getInt("QuestionID");
				String userQuestion = rs.getString("userQuestion");
				String petQuestion = rs.getString("petQuestion");

				Question question = new Question(questionID, userQuestion, petQuestion);
				petQuestionList.add(question);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return petQuestionList;
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.QuizResult;
import util.DButil;

public class QuizResultDAO {
	public List<QuizResult> findByUserId(String userId, String quizSessionId) {
		List<QuizResult> resultList = new ArrayList<>();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT q.QUIZ_ID, q.QUESTION, q.CHOICE1, q.CHOICE2, " +
	                "q.CHOICE3, q.CHOICE4, q.ANSWER, a.USER_ANSWER " +
	                "FROM QuizAnswer a " +
	                "JOIN PetQuiz q ON a.QUIZ_ID = q.QUIZ_ID " +
	                "WHERE a.USER_ID = ? AND a.QUIZ_SESSION_ID = ? " +
	                "ORDER BY q.QUIZ_ID DESC";
	        
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, userId);
	        pStmt.setString(2,  quizSessionId);
	        ResultSet rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	        	int quizId = rs.getInt("QUIZ_ID");
	        	String question = rs.getString("QUESTION");
	        	String choice1 = rs.getString("CHOICE1");
	        	String choice2 = rs.getString("CHOICE2");
	        	String choice3 = rs.getString("CHOICE3");
	        	String choice4 = rs.getString("CHOICE4");
	        	int answer = rs.getInt("ANSWER");
	        	int userAnswer = rs.getInt("USER_ANSWER");
	        	QuizResult qr = new QuizResult(quizId, question,choice1, choice2, choice3, choice4, answer, userAnswer);
	        	resultList.add(qr);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
}

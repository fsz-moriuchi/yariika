package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DogQuizResult;
import util.DButil;

public class DogQuizResultDAO {
	public List<DogQuizResult> findByUserId(String userId) {
		List<DogQuizResult> dogResultList = new ArrayList<>();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
	        String sql = "SELECT TOP 10 q.DOG_QUIZ_ID, q.QUESTION, q.CHOICE1, q.CHOICE2, q.CHOICE3, q.CHOICE4, q.ANSWER, a.DOG_USER_ANSWER FROM DogQuiz q JOIN DogQuizAnswer a ON q.DOG_QUIZ_ID = a.DOG_QUIZ_ID WHERE a.USER_ID = ? ORDER BY a.DOG_QUIZ_ANSWER_ID DESC";
	        
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, userId);
	        ResultSet rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	        	int dogQuizId = rs.getInt("DOG_QUIZ_ID");
	        	String question = rs.getString("QUESTION");
	        	String choice1 = rs.getString("CHOICE1");
	        	String choice2 = rs.getString("CHOICE2");
	        	String choice3 = rs.getString("CHOICE3");
	        	String choice4 = rs.getString("CHOICE4");
	        	int answer = rs.getInt("ANSWER");
	        	int dogUserAnswer = rs.getInt("DOG_USER_ANSWER");
	        	DogQuizResult dqr = new DogQuizResult(dogQuizId, question,choice1, choice2, choice3, choice4, answer, dogUserAnswer);
	        	dogResultList.add(dqr);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return dogResultList;
	}
	
}


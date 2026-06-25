package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.QuizAnswer;
import util.DButil;

public class QuizAnswerDAO {
	//userIdをインサート
	public void insert(QuizAnswer answer) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "INSERT INTO QuizAnswer (USER_ID, QUIZ_ID, USER_ANSWER, QUIZ_SESSION_ID) VALUES (?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, answer.getUserId());
			pStmt.setInt(2, answer.getQuizId());
			pStmt.setInt(3, answer.getUserAnswer());
			pStmt.setString(4,  answer.getQuizSessionId());
			pStmt.executeUpdate();
		}catch (Exception e) {
			throw new RuntimeException("DBエラーが発生しました", e);
		}
	}
}

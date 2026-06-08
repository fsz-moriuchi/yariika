package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.DogQuizAnswer;
import util.DButil;

public class DogQuizAnswerDAO {
	//userIdをインサート
	public void insert(DogQuizAnswer answer) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "INSERT INTO DogQuizAnswer (USER_ID, DOG_QUIZ_ID, DOG_USER_ANSWER) VALUES (?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, answer.getUserId());
			pStmt.setInt(2, answer.getDogQuizId());
			pStmt.setInt(3, answer.getDogUserAnswer());
			pStmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}


package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.CatQuizAnswer;
import util.DButil;

public class CatQuizAnswerDAO {
	//userIdをインサート
	public void insert(CatQuizAnswer answer) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "INSERT INTO CatQuizAnswer (USER_ID, CAT_QUIZ_ID, CAT_USER_ANSWER) VALUES (?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, answer.getUserId());
			pStmt.setInt(2, answer.getCatQuizId());
			pStmt.setInt(3, answer.getCatUserAnswer());
			pStmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	
	//クイズも問題、正解、自分の解答をSELECTする
	//SELECT * FROM CatQuizAnswer WHERE USER_ID = ?

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.PetQuiz;
import util.DButil;

public class PetQuizDAO {
	// クイズ一覧取得
	public List<PetQuiz> findByCategory(int categoryId) {
		List<PetQuiz> quizList = new ArrayList<>();
						
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT * FROM PetQuiz WHERE CATEGORY_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, categoryId);
			
			ResultSet rs = pStmt.executeQuery();
							
			while (rs.next()) {
				int quizId = rs.getInt("QUIZ_ID");
				String question = rs.getString("QUESTION");
				String choice1 = rs.getString("CHOICE1");
				String choice2 = rs.getString("CHOICE2");
				String choice3 = rs.getString("CHOICE3");
				String choice4 = rs.getString("CHOICE4");
				int answer = rs.getInt("ANSWER");
				int category = rs.getInt("CATEGORY_ID");
				PetQuiz quiz = new PetQuiz(quizId, question, choice1,choice2, choice3, choice4, answer, category);
				quizList.add(quiz);	//listに一件ずつ追加
			}
		}catch(Exception e) {
			throw new RuntimeException("DBエラーが発生しました", e);
		}
		//全件まとめて返す
		return quizList;
	}
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CatQuiz;
import util.DButil;

public class CatQuizDAO {
	
	// クイズ一覧取得
	public List<CatQuiz> findAll() {
		List<CatQuiz> catQuizList = new ArrayList<>();
				
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT * FROM CatQuiz";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
					
			while (rs.next()) {
				int id = rs.getInt("CAT_QUIZ_ID");
				String question = rs.getString("QUESTION");
				String choice1 = rs.getString("CHOICE1");
				String choice2 = rs.getString("CHOICE2");
				String choice3 = rs.getString("CHOICE3");
				String choice4 = rs.getString("CHOICE4");
				int answer = rs.getInt("ANSWER");
				CatQuiz quiz = new CatQuiz(id, question, choice1,choice2, choice3, choice4, answer);
				catQuizList.add(quiz);	//listに一件ずつ追加
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		//全件まとめて返す
		return catQuizList;
	}
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CatQuiz;

public class CatQuizDAO {
	//データベース接続に使用する情報
	private final String JDBC_URL ="jdbc:sqlserver://localhost\\\\\\\\SQLEXPRESS:58887;databaseName=master;"
	+"integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

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
		try(Connection conn = DriverManager.getConnection(JDBC_URL)){
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
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		//全件まとめて返す
		return catQuizList;
	}
}

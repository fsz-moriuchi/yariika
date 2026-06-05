package dao;

public class CatQuizResultDAO {
	/*
	public List<CatQuizResult> findByUserId(String userId) {
		List<CatQuizResult> list = new ArrayList<>();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
	        String sql = """SELECT q.QUESTION, q.CHOICE1, q.CHOICE2, q.CHOICE3, q.CHOICE4, q.ANSWER, a.CAT_USER_ANSWER FROM CatQuiz q JOIN CatQuizAnswer a ON q.CAT_QUIZ_ID = a.CAT_QUIZ_ID WHERE a.USER_ID = ?""";

	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, userId);
	        ResultSet rs = pStmt.executeQuery();
	        
	        while (rs.next()) {
	        	CatQuizResult r = new CatQuizResult();
	        	r.setQuestion(rs.getString("QUESTION"));
	        	r.setChoice1(rs.getString("CHOICE1"));
	        	r.setChoice2(rs.getString("CHOICE2"));
	        	r.setChoice3(rs.getString("CHOICE3"));
	        	r.setChoice4(rs.getString("CHOICE4"));
	        	r.setAnswer(rs.getInt("ANSWER"));
	        	r.setUserAnswer(rs.getInt("CAT_USER_ANSWER"));
	        	list.add(r);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	*/
}

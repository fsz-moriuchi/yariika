package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Question;

public class QuestionSurveyDAO {
	
	private final String JDBC_URL = "jdbc:sqlserver://localhost\\\\SQLEXPRESS:58956;databaseName=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

	public List<Question> findAllQuestion(){
		List<Question> questionSurveyList = new ArrayList<>();
	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL)){
			
			String sql = "SELECT * FROM QuestionSurvey";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()) {
				int QuestionID = rs.getInt("QuestionID");
				String userQuestion = rs.getString("userQuestion");
				String petQuestion = rs.getString("petQuestion");
				Question question = new Question(QuestionID,userQuestion,petQuestion);
				questionSurveyList.add(question);
			}
			
		
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return questionSurveyList;
	}

}

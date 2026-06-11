package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.UserLogin;
import model.UserSurvey;
import util.DButil;

public class UsersDAO {
	public User findByLogin(UserLogin login){
		User user = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT USER_ID, PASSWORD_HASH FROM USERS WHERE USER_ID = ? AND PASSWORD_HASH= ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUserId());
			pStmt.setString(2, login.getPasswordHash());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				String userId = rs.getString("USER_ID");
				String passwordHash = rs.getString("PASSWORD_HASH");
				user = new User(userId, passwordHash);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public boolean registerUser(User user){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO USERS(USER_ID, PASSWORD_HASH) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getPasswordHash());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
//新規ユーザーアンケート
	public boolean createUserSurvey(UserSurvey userSurvey) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO UserSurvey(USER_ID,QuestionID,SurveyChoiceID) VALUES(?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userSurvey.getUserId());
			pStmt.setInt(2, userSurvey.getQuestionID());
			pStmt.setInt(3,userSurvey.getSurveyChoiceID());

			int result = pStmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
//修正ユーザーアンケート
	public boolean updateUserSurvey(String userId,int questionID,int surveyChoiceID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String updateSql = "UPDATE UserSurvey SET SurveyChoiceID = ? WHERE USER_ID = ? AND QuestionID = ?";
			PreparedStatement updatepStmt = conn.prepareStatement(updateSql);

			updatepStmt.setInt(1,surveyChoiceID);
			updatepStmt.setString(2, userId);
			updatepStmt.setInt(3, questionID);
			int updateResult = updatepStmt.executeUpdate();
			
			//既存問題があれば更新成功
			if(updateResult == 1) {
				return true;
			}
			
			//アンケート新規問題があるとき　insert new question
			String insertSql = "INSERT INTO UserSurvey (USER_ID, QuestionID, SurveyChoiceID) VALUES(?, ?, ?)";
			PreparedStatement insertpStmt = conn.prepareStatement(insertSql);
			
			insertpStmt.setString(1, userId);
			insertpStmt.setInt(2, questionID);
			insertpStmt.setInt(3,surveyChoiceID);

			int insertResult = insertpStmt.executeUpdate();

			return insertResult == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
//show ユーザーアンケート
	public List<UserSurvey> showUserSurvey(String userId){
		List<UserSurvey> userSurveyList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT * FROM UserSurvey WHERE USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int questionID = rs.getInt("QuestionID");
				int surveyChoiceID = rs.getInt("SurveyChoiceID");
				UserSurvey userSurvey = new UserSurvey(userId,questionID,surveyChoiceID);	
				userSurveyList.add(userSurvey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userSurveyList;

		}
	
//パスワード変更
	public boolean updateUserPassword(User user){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE USERS SET PASSWORD_HASH = ? WHERE USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, user.getPasswordHash());
			pStmt.setString(2, user.getUserId());
			

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.UserInfo;
import util.DButil;

public class UserInfoDAO {
	
	//個人情報の入力・セッションからのuserIdの取得
	public boolean insert(UserInfo userInfo) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {

	        String sql = "INSERT INTO UserInfo (USER_ID, USER_NAME, USER_GENDER, USER_BIRTHDAY, USER_TEL, USER_MAIL, USER_ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        pStmt.setString(1, userInfo.getUserId()); 
	        pStmt.setString(2, userInfo.getUserName());
	        pStmt.setString(3, userInfo.getUserGender());
	        pStmt.setDate(4, userInfo.getUserBirthday());
	        pStmt.setString(5, userInfo.getUserTel());
	        pStmt.setString(6, userInfo.getUserMail());
	        pStmt.setString(7, userInfo.getUserAddress());

	        int result = pStmt.executeUpdate();
	        return result == 1;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	
	// 個人情報一覧取得
	public UserInfo findByUserId(String userId) {
		UserInfo userInfo = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			
			//SELECTしてマイページに表示
			String sql = "SELECT * FROM UserInfo WHERE USER_ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			
			ResultSet rs = pStmt.executeQuery();
			
			if (rs.next()) {
				int userInfoId = rs.getInt("USER_INFO_ID");
				String dbuserId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String userGender = rs.getString("USER_GENDER");
				Date userBirthday = rs.getDate("USER_BIRTHDAY");
				String userTel = rs.getString("USER_TEL");
				String userMail = rs.getString("USER_MAIL");
				String userAddress = rs.getString("USER_ADDRESS");
				userInfo = new UserInfo(userInfoId, dbuserId, userName, userGender, userBirthday, userTel, userMail, userAddress);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		//全件まとめて返す
		return userInfo;
	}
	
	
	//個人情報更新(修正)
	public boolean updateInfo(UserInfo userInfo) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {

	        String sql = "UPDATE UserInfo SET USER_NAME=?, USER_GENDER=?, USER_BIRTHDAY=?, USER_TEL=?, USER_MAIL=?, USER_ADDRESS=? WHERE USER_INFO_ID=?";
	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        pStmt.setString(1, userInfo.getUserName());
	        pStmt.setString(2, userInfo.getUserGender());
	        pStmt.setDate(3, userInfo.getUserBirthday());
	        pStmt.setString(4, userInfo.getUserTel());
	        pStmt.setString(5, userInfo.getUserMail());
	        pStmt.setString(6, userInfo.getUserAddress());
	        pStmt.setInt(7, userInfo.getUserInfoId()); 

	        int result = pStmt.executeUpdate();
	        return result == 1;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}


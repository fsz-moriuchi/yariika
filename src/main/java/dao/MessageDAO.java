package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Message;
import util.DButil;

public class MessageDAO {
	//送信
	public void insertMessage(Message message) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//INSERT
			String sql = "INSERT INTO Message (USER_ID, FACILITY_ID, petID, MESSAGE_TEXT, SENDER_TYPE) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, message.getUserId());
			pStmt.setString(2, message.getFacilityId());
			pStmt.setInt(3, message.getPetId());
			pStmt.setString(4,  message.getMessageText());
			pStmt.setString(5,  message.getSenderType());
			pStmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//取得
	public List<Message> getMessage(String userId, String facilityId, int petId) {
		List<Message> messageList = new ArrayList<>();

		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT * FROM Message WHERE USER_ID = ? AND FACILITY_ID = ? AND PET_ID = ? ORDER BY MESSAGE_ID ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, facilityId);
			pStmt.setInt(3,  petId);
			
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String uid = rs.getString("USER_ID");
				String fid = rs.getString("FACILITY_ID");
				int pid = rs.getInt("petID");
				String messageText = rs.getString("MESSAGE_TEXT");
				String senderType = rs.getString("SENDER_TYPE");
				
				Message message = new Message(uid, fid, pid, messageText, senderType);
				messageList.add(message);	//listに一件ずつ追加
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}
}

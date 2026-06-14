package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Message;
import model.MessageList;
import util.DButil;

public class MessageDAO {
	//メッセージ送信
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
			pStmt.setInt(3, message.getPetID());
			pStmt.setString(4,  message.getMessageText());
			pStmt.setString(5,  message.getSenderType());
			pStmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//メッセージ取得
	public List<Message> getMessage(String userId, String facilityId, int petID) {
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
			String sql = "SELECT * FROM Message WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? ORDER BY CREATED_AT ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, facilityId);
			pStmt.setInt(3,  petID);
			
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String uid = rs.getString("USER_ID");
				String fid = rs.getString("FACILITY_ID");
				int pid = rs.getInt("petID");
				String messageText = rs.getString("MESSAGE_TEXT");
				String senderType = rs.getString("SENDER_TYPE");
				Timestamp createdAt = rs.getTimestamp("CREATED_AT");
				
				Message message = new Message(uid, fid, pid, messageText, senderType, createdAt);
				messageList.add(message);	//listに一件ずつ追加
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}
	
	
	//メッセージ一覧表示
	public List<MessageList> findMessageListByFacilityId(String facilityId) {
		List<MessageList> messageList = new ArrayList<>();
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			//String sql = "SELECT USER_ID, petID, MAX(CREATED_AT) AS latest_time FROM Message WHERE FACILITY_ID = ? GROUP BY USER_ID, petID ORDER BY latest_time DESC";
			String sql = "SELECT m.USER_ID, u.USER_NAME, m.petID, p.name, MAX(m.CREATED_AT) AS latest_time\n"
					+ "FROM Message m "
					+ "LEFT JOIN UserInfo u ON m.USER_ID = u.USER_ID\n"
					+ "LEFT JOIN PetInformation p ON m.petID = p.petID\n"
					+ "WHERE m.FACILITY_ID = ?\n"
					+ "GROUP BY m.USER_ID, u.USER_NAME, m.petID, p.name\n"
					+ "ORDER BY latest_time DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);
					
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				int petID = rs.getInt("petID");
				String petName = rs.getString("name");
				Timestamp latestTime = rs.getTimestamp("latest_time");

	            MessageList mList = new MessageList(userId, userName, petID, petName, latestTime);
	            messageList.add(mList);
	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				return messageList;
	}
}
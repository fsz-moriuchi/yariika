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
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//INSERT
			String sql = "INSERT INTO Message (USER_ID, FACILITY_ID, petID, MESSAGE_TEXT, SENDER_TYPE, IS_READ) VALUES (?, ?, ?, ?, ?, 0)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, message.getUserId());
			pStmt.setString(2, message.getFacilityId());
			pStmt.setInt(3, message.getPetID());
			pStmt.setString(4, message.getMessageText());
			pStmt.setString(5, message.getSenderType());
			pStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//メッセージの通知機能_既読処理
	public void markAsRead(String userId, String facilityId, int petID, String viewerType) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//INSERT
			String sql = "";

			if (viewerType.equals("USER")) {
				//ユーザーが見た→店舗からのメッセージを既読に
				sql = "UPDATE Message SET IS_READ = 1 \n"
						+ "WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? \n"
						+ "AND SENDER_TYPE = 'FACILITY' AND IS_READ = 0";
			} else {
				//店舗が見た→ユーザーからのメッセージを既読に
				sql = "UPDATE Message SET IS_READ = 1 \n"
						+ "WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? \n"
						+ "AND SENDER_TYPE = 'USER' AND IS_READ = 0";
			}
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, facilityId);
			pStmt.setInt(3, petID);
			pStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//メッセージ取得
	public List<Message> getMessage(String userId, String facilityId, int petID) {
		List<Message> messageList = new ArrayList<>();

		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT * FROM Message WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? ORDER BY CREATED_AT ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, facilityId);
			pStmt.setInt(3, petID);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String uid = rs.getString("USER_ID");
				String fid = rs.getString("FACILITY_ID");
				int pid = rs.getInt("petID");
				String messageText = rs.getString("MESSAGE_TEXT");
				String senderType = rs.getString("SENDER_TYPE");
				Timestamp createdAt = rs.getTimestamp("CREATED_AT");

				Message message = new Message(uid, fid, pid, messageText, senderType, createdAt);
				messageList.add(message); //listに一件ずつ追加
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}

	//店舗側メッセージ一覧表示
	public List<MessageList> findMessageListByFacilityId(String facilityId) {
		List<MessageList> messageList = new ArrayList<>();
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT m.USER_ID, u.USER_NAME, m.petID, p.name, MAX(m.CREATED_AT) AS latest_time, \n"
					+ "SUM(CASE WHEN m.SENDER_TYPE = 'USER' AND m.IS_READ = 0 THEN 1 ELSE 0 END) AS unread_count\n"
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
				int unreadCount = rs.getInt("unread_count");

				MessageList mList = new MessageList(userId, userName, petID, petName, latestTime, unreadCount);
				messageList.add(mList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}

	//ユーザー側メッセージ一覧表示
	public List<MessageList> findMessageListByUserId(String userId) {
		List<MessageList> messageList = new ArrayList<>();
		//JDBCドライバを読み込む
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースに接続
		try (Connection conn = DButil.getConnection()) {
			//SELECT文を準備
			String sql = "SELECT m.FACILITY_ID, f.facilityName, m.petID, p.name, MAX(m.CREATED_AT) AS latest_time, \n"
					+ "SUM(CASE WHEN m.SENDER_TYPE = 'FACILITY' AND m.IS_READ = 0 THEN 1 ELSE 0 END) AS unread_count\n"
					+ "FROM Message m "
					+ "LEFT JOIN FacilityInformation f ON m.FACILITY_ID = f.FACILITY_ID\n"
					+ "LEFT JOIN PetInformation p ON m.petID = p.petID\n"
					+ "WHERE m.USER_ID = ?\n"
					+ "GROUP BY m.FACILITY_ID, f.FacilityName, m.petID, p.name\n"
					+ "ORDER BY latest_time DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String facilityId = rs.getString("FACILITY_ID");
				String facilityName = rs.getString("FacilityName");
				int petID = rs.getInt("petID");
				String petName = rs.getString("name");
				Timestamp latestTime = rs.getTimestamp("latest_time");
				int unreadCount = rs.getInt("unread_count");

				MessageList mList = new MessageList(facilityId, facilityName, petID, petName, latestTime, true,
						unreadCount);
				messageList.add(mList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}

	//未読数の表示（ユーザー）
	public int countUnreadByUserId(String userId) {

		int count = 0;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT COUNT(*) AS unread_count "
					+ "FROM Message "
					+ "WHERE USER_ID = ? "
					+ "AND SENDER_TYPE = 'FACILITY' "
					+ "AND IS_READ = 0";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("unread_count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	//未読数の表示（施設）
	public int countUnreadByFacilityId(String facilityId) {

		int count = 0;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT COUNT(*) AS unread_count "
					+ "FROM Message "
					+ "WHERE FACILITY_ID = ? "
					+ "AND SENDER_TYPE = 'USER' "
					+ "AND IS_READ = 0";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("unread_count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
}
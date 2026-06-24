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

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static final String SQL_INSERT_MESSAGE = "INSERT INTO Message (USER_ID, FACILITY_ID, petID, MESSAGE_TEXT, SENDER_TYPE, IS_READ) "
			+ "VALUES (?, ?, ?, ?, ?, 0)";

	private static final String SQL_MARK_READ_BY_USER = "UPDATE Message SET IS_READ = 1 "
			+ "WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? "
			+ "AND SENDER_TYPE = 'FACILITY' AND IS_READ = 0";

	private static final String SQL_MARK_READ_BY_FACILITY = "UPDATE Message SET IS_READ = 1 "
			+ "WHERE USER_ID = ? AND FACILITY_ID = ? AND petID = ? "
			+ "AND SENDER_TYPE = 'USER' AND IS_READ = 0";

	private static final String SQL_GET_MESSAGES = "SELECT m.*, u.USER_NAME, f.facilityName "
			+ "FROM Message m "
			+ "LEFT JOIN UserInfo u ON m.USER_ID = u.USER_ID "
			+ "LEFT JOIN FacilityInformation f ON m.FACILITY_ID = f.FACILITY_ID "
			+ "WHERE m.USER_ID = ? AND m.FACILITY_ID = ? AND m.petID = ? "
			+ "ORDER BY m.CREATED_AT ASC";

	private static final String SQL_FACILITY_MESSAGE_LIST = "SELECT m.USER_ID, u.USER_NAME, m.petID, p.name, MAX(m.CREATED_AT) AS latest_time, "
			+ "SUM(CASE WHEN m.SENDER_TYPE = 'USER' AND m.IS_READ = 0 THEN 1 ELSE 0 END) AS unread_count "
			+ "FROM Message m "
			+ "LEFT JOIN UserInfo u ON m.USER_ID = u.USER_ID "
			+ "LEFT JOIN PetInformation p ON m.petID = p.petID "
			+ "WHERE m.FACILITY_ID = ? "
			+ "GROUP BY m.USER_ID, u.USER_NAME, m.petID, p.name "
			+ "ORDER BY latest_time DESC";

	private static final String SQL_USER_MESSAGE_LIST = "SELECT m.FACILITY_ID, f.facilityName, m.petID, p.name, MAX(m.CREATED_AT) AS latest_time, "
			+ "SUM(CASE WHEN m.SENDER_TYPE = 'FACILITY' AND m.IS_READ = 0 THEN 1 ELSE 0 END) AS unread_count "
			+ "FROM Message m "
			+ "LEFT JOIN FacilityInformation f ON m.FACILITY_ID = f.FACILITY_ID "
			+ "LEFT JOIN PetInformation p ON m.petID = p.petID "
			+ "WHERE m.USER_ID = ? "
			+ "GROUP BY m.FACILITY_ID, f.FacilityName, m.petID, p.name "
			+ "ORDER BY latest_time DESC";

	private static final String SQL_COUNT_UNREAD_BY_USER = "SELECT COUNT(*) AS unread_count "
			+ "FROM Message "
			+ "WHERE USER_ID = ? "
			+ "AND SENDER_TYPE = 'FACILITY' "
			+ "AND IS_READ = 0";

	private static final String SQL_COUNT_UNREAD_BY_FACILITY = "SELECT COUNT(*) AS unread_count "
			+ "FROM Message "
			+ "WHERE FACILITY_ID = ? "
			+ "AND SENDER_TYPE = 'USER' "
			+ "AND IS_READ = 0";

	// メッセージ送信
	public void insertMessage(Message message) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_MESSAGE)) {

			bindInsertMessage(stmt, message);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// メッセージの通知機能_既読処理
	public void markAsRead(String userId, String facilityId, int petID, String viewerType) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		String sql = isUserViewer(viewerType) ? SQL_MARK_READ_BY_USER : SQL_MARK_READ_BY_FACILITY;

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			bindMessageKey(stmt, userId, facilityId, petID);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// メッセージ取得
	public List<Message> getMessage(String userId, String facilityId, int petID) {
		List<Message> messageList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_GET_MESSAGES)) {

			bindMessageKey(stmt, userId, facilityId, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					messageList.add(toMessage(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	// 店舗側メッセージ一覧表示
	public List<MessageList> findMessageListByFacilityId(String facilityId) {
		List<MessageList> messageList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FACILITY_MESSAGE_LIST)) {

			stmt.setString(1, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					messageList.add(toFacilityMessageList(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	// ユーザー側メッセージ一覧表示
	public List<MessageList> findMessageListByUserId(String userId) {
		List<MessageList> messageList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_USER_MESSAGE_LIST)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					messageList.add(toUserMessageList(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	// 未読数の表示（ユーザー）
	public int countUnreadByUserId(String userId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_UNREAD_BY_USER)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("unread_count");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 未読数の表示（施設）
	public int countUnreadByFacilityId(String facilityId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_UNREAD_BY_FACILITY)) {

			stmt.setString(1, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("unread_count");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private boolean isUserViewer(String viewerType) {
		return "USER".equals(viewerType);
	}

	private void bindInsertMessage(PreparedStatement stmt, Message message) throws Exception {
		stmt.setString(1, message.getUserId());
		stmt.setString(2, message.getFacilityId());
		stmt.setInt(3, message.getPetID());
		stmt.setString(4, message.getMessageText());
		stmt.setString(5, message.getSenderType());
	}

	private void bindMessageKey(PreparedStatement stmt, String userId, String facilityId, int petID)
			throws Exception {
		stmt.setString(1, userId);
		stmt.setString(2, facilityId);
		stmt.setInt(3, petID);
	}

	private Message toMessage(ResultSet rs) throws Exception {
		String uid = rs.getString("USER_ID");
		String fid = rs.getString("FACILITY_ID");
		int pid = rs.getInt("petID");
		String messageText = rs.getString("MESSAGE_TEXT");
		String senderType = rs.getString("SENDER_TYPE");
		Timestamp createdAt = rs.getTimestamp("CREATED_AT");
		String userName = rs.getString("USER_NAME");
		String facilityName = rs.getString("facilityName");

		return new Message(uid, fid, pid, messageText, senderType, createdAt, userName, facilityName);
	}

	private MessageList toFacilityMessageList(ResultSet rs) throws Exception {
		String userId = rs.getString("USER_ID");
		String userName = rs.getString("USER_NAME");
		int petID = rs.getInt("petID");
		String petName = rs.getString("name");
		Timestamp latestTime = rs.getTimestamp("latest_time");
		int unreadCount = rs.getInt("unread_count");

		return new MessageList(userId, userName, petID, petName, latestTime, unreadCount);
	}

	private MessageList toUserMessageList(ResultSet rs) throws Exception {
		String facilityId = rs.getString("FACILITY_ID");
		String facilityName = rs.getString("FacilityName");
		int petID = rs.getInt("petID");
		String petName = rs.getString("name");
		Timestamp latestTime = rs.getTimestamp("latest_time");
		int unreadCount = rs.getInt("unread_count");

		return new MessageList(facilityId, facilityName, petID, petName, latestTime, true, unreadCount);
	}
}
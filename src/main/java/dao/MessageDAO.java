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

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public void insertMessage(Message message) {
		executeUpdate(SQL_INSERT_MESSAGE, stmt -> bindInsertMessage(stmt, message));
	}

	public void markAsRead(String userId, String facilityId, int petId, String viewerType) {
		String sql = isUserViewer(viewerType) ? SQL_MARK_READ_BY_USER : SQL_MARK_READ_BY_FACILITY;
		executeUpdate(sql, stmt -> bindMessageKey(stmt, userId, facilityId, petId));
	}

	public List<Message> getMessage(String userId, String facilityId, int petId) {
		List<Message> messageList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_GET_MESSAGES)) {

			bindMessageKey(statement, userId, facilityId, petId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					messageList.add(toMessage(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	public List<MessageList> findMessageListByFacilityId(String facilityId) {
		List<MessageList> messageList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FACILITY_MESSAGE_LIST)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					messageList.add(toFacilityMessageList(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	public List<MessageList> findMessageListByUserId(String userId) {
		List<MessageList> messageList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_USER_MESSAGE_LIST)) {

			statement.setString(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					messageList.add(toUserMessageList(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageList;
	}

	public int countUnreadByUserId(String userId) {
		return countUnread(SQL_COUNT_UNREAD_BY_USER, userId);
	}

	public int countUnreadByFacilityId(String facilityId) {
		return countUnread(SQL_COUNT_UNREAD_BY_FACILITY, facilityId);
	}

	private void executeUpdate(String sql, SqlBinder binder) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			binder.bind(statement);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int countUnread(String sql, String id) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("unread_count");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private boolean isUserViewer(String viewerType) {
		return "USER".equals(viewerType);
	}

	private void bindInsertMessage(PreparedStatement statement, Message message) throws Exception {
		statement.setString(1, message.getUserId());
		statement.setString(2, message.getFacilityId());
		statement.setInt(3, message.getPetID());
		statement.setString(4, message.getMessageText());
		statement.setString(5, message.getSenderType());
	}

	private void bindMessageKey(PreparedStatement statement, String userId, String facilityId, int petId)
			throws Exception {
		statement.setString(1, userId);
		statement.setString(2, facilityId);
		statement.setInt(3, petId);
	}

	private Message toMessage(ResultSet resultSet) throws Exception {
		String userId = resultSet.getString("USER_ID");
		String facilityId = resultSet.getString("FACILITY_ID");
		int petId = resultSet.getInt("petID");
		String messageText = resultSet.getString("MESSAGE_TEXT");
		String senderType = resultSet.getString("SENDER_TYPE");
		Timestamp createdAt = resultSet.getTimestamp("CREATED_AT");
		String userName = resultSet.getString("USER_NAME");
		String facilityName = resultSet.getString("facilityName");

		return new Message(userId, facilityId, petId, messageText, senderType, createdAt, userName, facilityName);
	}

	private MessageList toFacilityMessageList(ResultSet resultSet) throws Exception {
		String userId = resultSet.getString("USER_ID");
		String userName = resultSet.getString("USER_NAME");
		int petId = resultSet.getInt("petID");
		String petName = resultSet.getString("name");
		Timestamp latestTime = resultSet.getTimestamp("latest_time");
		int unreadCount = resultSet.getInt("unread_count");

		return new MessageList(userId, userName, petId, petName, latestTime, unreadCount);
	}

	private MessageList toUserMessageList(ResultSet resultSet) throws Exception {
		String facilityId = resultSet.getString("FACILITY_ID");
		String facilityName = resultSet.getString("FacilityName");
		int petId = resultSet.getInt("petID");
		String petName = resultSet.getString("name");
		Timestamp latestTime = resultSet.getTimestamp("latest_time");
		int unreadCount = resultSet.getInt("unread_count");

		return new MessageList(facilityId, facilityName, petId, petName, latestTime, true, unreadCount);
	}

	@FunctionalInterface
	private interface SqlBinder {
		void bind(PreparedStatement statement) throws Exception;
	}
}
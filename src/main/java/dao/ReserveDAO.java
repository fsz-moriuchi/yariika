package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Reserve;
import model.ReserveView;
import util.DButil;

public class ReserveDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static final String SQL_INSERT_RESERVE = "INSERT INTO Reserve(petID, USER_ID, reserveTime) VALUES(?, ?, ?)";

	private static final String SQL_FIND_RESERVED_TIME_BY_FACILITY_AND_DATE = "SELECT R.reserveTime "
			+ "FROM Reserve R "
			+ "JOIN Pet P ON P.petID = R.petID "
			+ "WHERE P.FACILITY_ID = ? "
			+ "AND CAST(R.reserveTime AS DATE) = ?";

	private static final String SQL_EXISTS_RESERVE_BY_PET_ID = "SELECT COUNT(*) AS CNT FROM Reserve WHERE petID = ?";

	private static final String SQL_FIND_BY_FACILITY_ID = "SELECT R.reservationID, R.petID, R.USER_ID, R.reserveTime "
			+ "FROM Reserve R "
			+ "JOIN Pet P "
			+ "ON R.petID = P.petID "
			+ "WHERE P.FACILITY_ID = ? "
			+ "ORDER BY R.reserveTime";

	private static final String SQL_DELETE_RESERVATION = "DELETE FROM Reserve WHERE reservationID = ?";

	private static final String SQL_UPDATE_RESERVE_TIME = "UPDATE Reserve SET reserveTime = ? WHERE reservationID = ?";

	private static final String SQL_RESERVE_CHECK = "SELECT r.reservationID,r.petID,r.USER_ID,r.reserveTime,\r\n"
			+ "pi.name,pi.gender,pi.age,pi.imagePath,p.CATEGORY_ID,\r\n"
			+ "fi.facilityName,fi.address,fi.tel,fi.mail,fi.openTime,fi.closeTime,STRING_AGG(fcd.closedDay,',') AS closedDay,\r\n"
			+ "ui.USER_NAME,ui.USER_GENDER, ui.USER_BIRTHDAY, ui.USER_TEL, ui.USER_MAIL\r\n"
			+ "FROM Reserve r\r\n"
			+ "JOIN Pet p ON r.petID = p.petID\r\n"
			+ "JOIN PetInformation pi ON p.petID = pi.petID\r\n"
			+ "JOIN FacilityInformation fi ON p.FACILITY_ID = fi.FACILITY_ID\r\n"
			+ "LEFT JOIN FacilityClosedDay fcd ON fi.FACILITY_ID = fcd.FACILITY_ID\r\n"
			+ "LEFT JOIN UserInfo ui ON r.USER_ID = ui.USER_ID\n"
			+ "WHERE r.USER_ID = ?\n"
			+ "GROUP BY r.reservationID,r.petID,r.USER_ID,r.reserveTime,pi.name,pi.gender,pi.age,pi.imagePath,p.CATEGORY_ID,fi.facilityName,fi.address,fi.tel,fi.mail,fi.openTime,fi.closeTime,ui.USER_NAME,ui.USER_GENDER,ui.USER_BIRTHDAY,ui.USER_TEL,ui.USER_MAIL";

	private static final String SQL_COUNT_TODAY_RESERVE = "SELECT COUNT(*) AS CNT FROM Reserve R JOIN Pet P ON R.petID = P.petID WHERE P.FACILITY_ID = ? AND CAST(R.reserveTime AS DATE) = ?";

	private static final String SQL_FIND_NEXT_RESERVE = "SELECT TOP 1 "
			+ "R.reservationID, "
			+ "R.petID, "
			+ "R.USER_ID, "
			+ "R.reserveTime "
			+ "FROM Reserve R "
			+ "JOIN Pet P "
			+ "ON R.petID = P.petID "
			+ "WHERE P.FACILITY_ID = ? "
			+ "AND R.reserveTime >= GETDATE() "
			+ "ORDER BY R.reserveTime ASC";

	private static final String SQL_FIND_BY_FACILITY_ID_FOR_VIEW = "SELECT "
			+ "R.reservationID, "
			+ "R.petID, "
			+ "R.USER_ID, "
			+ "R.reserveTime, "
			+ "PI.name AS petName, "
			+ "PI.imagePath, "
			+ "PI.gender AS petGender, "
			+ "PI.age AS petAge, "
			+ "C.CATEGORY_NAME AS categoryName, "
			+ "UI.USER_NAME AS userName, "
			+ "UI.USER_BIRTHDAY AS userBirthday, "
			+ "UI.USER_GENDER AS userGender, "
			+ "UI.USER_TEL AS userTel, "
			+ "UI.USER_MAIL AS userMail "
			+ "FROM Reserve R "
			+ "JOIN Pet P ON R.petID = P.petID "
			+ "JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID "
			+ "LEFT JOIN PetInformation PI ON R.petID = PI.petID "
			+ "LEFT JOIN UserInfo UI ON R.USER_ID = UI.USER_ID "
			+ "WHERE P.FACILITY_ID = ? "
			+ "ORDER BY R.reserveTime ASC";

	// 予約を追加するメソッド
	public boolean insertReserve(Reserve reserve) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_RESERVE)) {

			bindInsertReserve(stmt, reserve);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 予約時に日付を選択すると予約可能な時間を表示するためのメソッド
	public List<LocalTime> findByFacilityAndDate(String facilityID, LocalDate reserveDate) {
		List<LocalTime> reservedTimeList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_RESERVED_TIME_BY_FACILITY_AND_DATE)) {

			stmt.setString(1, facilityID);
			stmt.setDate(2, Date.valueOf(reserveDate));

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					reservedTimeList.add(rs.getTimestamp("reserveTime").toLocalDateTime().toLocalTime());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservedTimeList;
	}

	// すでにペットが予約されているか判定するメソッド
	public boolean existsReserveByPetID(int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_EXISTS_RESERVE_BY_PET_ID)) {

			stmt.setInt(1, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("CNT") > 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// 施設の予約一覧を表示するためのメソッド(没)
	public List<Reserve> findByFacilityID(String facilityID) {
		List<Reserve> reservedDataList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_FACILITY_ID)) {

			stmt.setString(1, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					reservedDataList.add(toReserve(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservedDataList;
	}

	// 予約を削除するメソッド
	public boolean deleteReservation(int reservationID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_RESERVATION)) {

			stmt.setInt(1, reservationID);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 予約日時を変更するためのメソッド
	public boolean updateDateTime(int reservationID, LocalDateTime reserveTime) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_RESERVE_TIME)) {

			stmt.setTimestamp(1, Timestamp.valueOf(reserveTime));
			stmt.setInt(2, reservationID);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ユーザーが予約を確認するメソッド
	public Reserve reserveCheck(String userID) {
		Reserve reserve = null;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_RESERVE_CHECK)) {

			stmt.setString(1, userID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					reserve = toReserveCheckResult(rs, userID);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserve;
	}

	// 今日の予約件数を数えるメソッド
	public int countTodayReserve(String facilityID) {
		int count = 0;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_TODAY_RESERVE)) {

			stmt.setString(1, facilityID);
			stmt.setDate(2, Date.valueOf(LocalDate.now()));

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt("CNT");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	// 次の予約を表示するためのメソッド
	public Reserve findnextReserve(String facilityID) {
		Reserve reserve = null;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_NEXT_RESERVE)) {

			stmt.setString(1, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					reserve = new Reserve(
							rs.getInt("reservationID"),
							rs.getInt("petID"),
							rs.getString("USER_ID"),
							rs.getTimestamp("reserveTime").toLocalDateTime());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserve;
	}

	// 施設の予約一覧を表示するためのメソッド
	public List<ReserveView> findByFacilityIDForView(String facilityID) {
		List<ReserveView> reserveViewList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_FACILITY_ID_FOR_VIEW)) {

			stmt.setString(1, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

				while (rs.next()) {
					reserveViewList.add(toReserveView(rs, formatter));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserveViewList;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindInsertReserve(PreparedStatement stmt, Reserve reserve) throws Exception {
		stmt.setInt(1, reserve.getPetID());
		stmt.setString(2, reserve.getUserID());
		stmt.setTimestamp(3, Timestamp.valueOf(reserve.getReserveTime()));
	}

	private Reserve toReserve(ResultSet rs) throws Exception {
		return new Reserve(
				rs.getInt("reservationID"),
				rs.getInt("petID"),
				rs.getString("USER_ID"),
				rs.getTimestamp("reserveTime").toLocalDateTime());
	}

	private Reserve toReserveCheckResult(ResultSet rs, String userID) throws Exception {
		int reservationID = rs.getInt("reservationID");
		int petID = rs.getInt("petID");
		LocalDateTime reserveTime = rs.getTimestamp("reserveTime").toLocalDateTime();
		String petName = rs.getString("name");
		String gender = rs.getString("gender");
		int age = rs.getInt("age");
		String imagePath = rs.getString("imagePath");
		int categoryId = rs.getInt("CATEGORY_ID");
		String facilityName = rs.getString("facilityName");
		String address = rs.getString("address");
		String tel = rs.getString("tel");
		String mail = rs.getString("mail");
		LocalTime openTime = rs.getTimestamp("openTime").toLocalDateTime().toLocalTime();
		LocalTime closeTime = rs.getTimestamp("closeTime").toLocalDateTime().toLocalTime();
		String closedDay = rs.getString("closedDay");
		String userName = rs.getString("USER_NAME");
		String userGender = rs.getString("USER_GENDER");
		Date userBirthday = rs.getDate("USER_BIRTHDAY");
		String userTel = rs.getString("USER_TEL");
		String userMail = rs.getString("USER_MAIL");

		return new Reserve(
				reservationID,
				petID,
				userID,
				reserveTime,
				petName,
				gender,
				age,
				imagePath,
				categoryId,
				facilityName,
				address,
				tel,
				mail,
				openTime,
				closeTime,
				closedDay,
				userName,
				userGender,
				userBirthday,
				userTel,
				userMail);
	}

	private ReserveView toReserveView(ResultSet rs, DateTimeFormatter formatter) throws Exception {
		int reservationID = rs.getInt("reservationID");
		int petID = rs.getInt("petID");
		String userID = rs.getString("USER_ID");
		LocalDateTime reserveTime = rs.getTimestamp("reserveTime").toLocalDateTime();
		String formattedReserveTime = reserveTime.format(formatter);
		String petName = rs.getString("petName");
		String imagePath = rs.getString("imagePath");
		String userName = rs.getString("userName");
		String userTel = rs.getString("userTel");
		String userMail = rs.getString("userMail");
		String categoryName = rs.getString("categoryName");
		String petGender = rs.getString("petGender");
		String userGender = rs.getString("userGender");
		int petAge = rs.getInt("petAge");

		int userAge = 0;
		if (rs.getDate("userBirthday") != null) {
			LocalDate birthday = rs.getDate("userBirthday").toLocalDate();
			userAge = Period.between(birthday, LocalDate.now()).getYears();
		}

		return new ReserveView(
				reservationID,
				petID,
				petName,
				imagePath,
				categoryName,
				petGender,
				petAge,
				userID,
				userName,
				userAge,
				userGender,
				userTel,
				userMail,
				reserveTime,
				formattedReserveTime);
	}
}
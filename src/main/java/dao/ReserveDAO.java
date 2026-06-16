package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Reserve;
import util.DButil;

public class ReserveDAO {
	//予約を追加するメソッド
	public boolean insertReserve(Reserve reserve) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO Reserve(petID, USER_ID, reserveTime) VALUES( ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, reserve.getPetID());
			pStmt.setString(2, reserve.getUserID());
			pStmt.setTimestamp(3, Timestamp.valueOf(reserve.getReserveTime()));

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

	//予約時に日付を選択すると予約可能な時間を表示するためのメソッド
	public List<LocalTime> findByFacilityAndDate(String facilityID, LocalDate reserveDate) {
		List<LocalTime> reservedTimeList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT R.reserveTime " +
					"FROM Reserve R " +
					"JOIN Pet P ON P.petID = R.petID " +
					"WHERE P.FACILITY_ID = ? " +
					"AND CAST(R.reserveTime AS DATE) = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityID);
			pStmt.setDate(2, java.sql.Date.valueOf(reserveDate));

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				LocalTime reserveTime = rs.getTimestamp("reserveTime")
						.toLocalDateTime()
						.toLocalTime();

				reservedTimeList.add(reserveTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservedTimeList;
	}

	//すでにペットが予約されているか判定するメソッド
	public boolean existsReserveByPetID(int petID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT COUNT(*) AS CNT FROM Reserve WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, petID);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("CNT") > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	//施設の予約一覧を表示するためのメソッド
	public List<Reserve> findByFacilityID(String facilityID) {

		List<Reserve> reservedDataList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT R.reservationID, R.petID, R.USER_ID, R.reserveTime "
					+ "FROM Reserve R "
					+ "JOIN Pet P "
					+ "ON R.petID = P.petID "
					+ "WHERE P.FACILITY_ID = ? "
					+ "ORDER BY R.reserveTime";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				int reservationID = rs.getInt("reservationID");

				int petID = rs.getInt("petID");

				String userID = rs.getString("USER_ID");

				LocalDateTime reserveTime = rs.getTimestamp("reserveTime")
						.toLocalDateTime();

				Reserve reserve = new Reserve(
						reservationID,
						petID,
						userID,
						reserveTime);

				reservedDataList.add(reserve);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reservedDataList;
	}

	//予約を削除するメソッド
	public boolean deleteReservation(int reservationID) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "DELETE FROM Reserve WHERE reservationID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, reservationID);

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

	//予約日時を変更するためのメソッド
	public boolean updateDateTime(int reservationID, LocalDateTime reserveTime) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE Reserve SET reserveTime = ? WHERE reservationID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setTimestamp(1, Timestamp.valueOf(reserveTime));
			pStmt.setInt(2, reservationID);

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

	//ユーザーが予約を確認するメソッド
	public Reserve reserveCheck(String userID) {
		Reserve reserve = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			//String sql = "SELECT reservationID, petID, USER_ID, reserveTime FROM Reserve WHERE USER_ID = ?";
			String sql = "SELECT r.reservationID,r.petID,r.USER_ID,r.reserveTime,\r\n"
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
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
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
				reserve = new Reserve(reservationID, petID, userID, reserveTime, petName, gender, age, imagePath, categoryId, facilityName, address, tel, mail, openTime, closeTime, closedDay,userName,userGender,userBirthday,userTel,userMail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserve;
	}

	//今日の予約件数を数えるメソッド
	public int countTodayReserve(String facilityID) {
		int count = 0;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT COUNT(*) AS CNT FROM Reserve R JOIN Pet P ON R.petID = P.petID WHERE P.FACILITY_ID = ? AND CAST(R.reserveTime AS DATE) = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityID);
			pStmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("CNT");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	//次の予約を表示するためのメソッド
	public Reserve findnextReserve(String facilityID) {

		Reserve reserve = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT TOP 1 "
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

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityID);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				reserve = new Reserve(
						rs.getInt("reservationID"),
						rs.getInt("petID"),
						rs.getString("USER_ID"),
						rs.getTimestamp("reserveTime").toLocalDateTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserve;
	}

}

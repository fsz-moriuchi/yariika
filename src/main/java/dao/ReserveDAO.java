package dao;

import java.sql.Connection;
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

			String sql = "SELECT reservationID, petID, USER_ID, reserveTime FROM Reserve WHERE USER_ID = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int reservationID = rs.getInt("reservationID");
				int petID = rs.getInt("petID");
				LocalDateTime reserveTime = rs.getTimestamp("reserveTime").toLocalDateTime();
				reserve = new Reserve(reservationID, petID, userID, reserveTime);
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

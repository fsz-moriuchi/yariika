package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DButil;

public class FacilityClosedDayDAO {
	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_FIND_BY_FACILITY_ID = "SELECT closedDay FROM FacilityClosedDay " +
			"WHERE FACILITY_ID = ? " +
			"ORDER BY CASE closedDay " +
			"WHEN 'MONDAY' THEN 1 " +
			"WHEN 'TUESDAY' THEN 2 " +
			"WHEN 'WEDNESDAY' THEN 3 " +
			"WHEN 'THURSDAY' THEN 4 " +
			"WHEN 'FRIDAY' THEN 5 " +
			"WHEN 'SATURDAY' THEN 6 " +
			"WHEN 'SUNDAY' THEN 7 " +
			"ELSE 8 END";

	private static final String SQL_DELETE_BY_FACILITY_ID = "DELETE FROM FacilityClosedDay WHERE FACILITY_ID = ?";
	private static final String SQL_INSERT = "INSERT INTO FacilityClosedDay(FACILITY_ID, closedDay) VALUES(?, ?)";

	// 予約判定のための定休日一覧取得
	public List<String> findByFacilityID(String facilityID) {
		List<String> closedDayList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_FACILITY_ID)) {

			bindFacilityId(stmt, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					closedDayList.add(rs.getString("closedDay"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return closedDayList;
	}

	// 定休日を一旦削除する
	public boolean deleteByFacilityID(String facilityID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_FACILITY_ID)) {

			bindFacilityId(stmt, facilityID);
			stmt.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 定休日を1件追加する
	public boolean insertByFacilityID(String facilityID, String closedDay) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

			bindFacilityIdAndClosedDay(stmt, facilityID, closedDay);
			stmt.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindFacilityId(PreparedStatement stmt, String facilityID) throws Exception {
		stmt.setString(1, facilityID);
	}

	private void bindFacilityIdAndClosedDay(PreparedStatement stmt, String facilityID, String closedDay)
			throws Exception {
		stmt.setString(1, facilityID);
		stmt.setString(2, closedDay);
	}
}
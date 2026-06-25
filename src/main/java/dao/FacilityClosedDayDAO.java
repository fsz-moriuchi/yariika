package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DButil;

public class FacilityClosedDayDAO {

	private static final String JDBC_DRIVER =
			"com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static final String FIND_BY_FACILITY_ID_SQL =
			"SELECT closedDay " +
			"FROM FacilityClosedDay " +
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

	private static final String DELETE_BY_FACILITY_ID_SQL =
			"DELETE FROM FacilityClosedDay WHERE FACILITY_ID = ?";

	private static final String INSERT_BY_FACILITY_ID_SQL =
			"INSERT INTO FacilityClosedDay(FACILITY_ID, closedDay) VALUES(?, ?)";

	// 指定施設の定休日一覧を取得するメソッド
	public List<String> findByFacilityID(String facilityID) {
		loadDriver();

		List<String> closedDayList = new ArrayList<>();

		try (Connection conn = DButil.getConnection();
				PreparedStatement pStmt =
						conn.prepareStatement(FIND_BY_FACILITY_ID_SQL)) {

			pStmt.setString(1, facilityID);

			try (ResultSet rs = pStmt.executeQuery()) {
				while (rs.next()) {
					String closedDay = rs.getString("closedDay");
					closedDayList.add(closedDay);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return closedDayList;
	}

	// 指定施設の定休日を一度すべて削除するメソッド
	public boolean deleteByFacilityID(String facilityID) {
		loadDriver();

		try (Connection conn = DButil.getConnection();
				PreparedStatement pStmt =
						conn.prepareStatement(DELETE_BY_FACILITY_ID_SQL)) {

			pStmt.setString(1, facilityID);
			pStmt.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 指定施設の定休日を登録するメソッド
	public boolean insertByFacilityID(String facilityID, String closedDay) {
		loadDriver();

		try (Connection conn = DButil.getConnection();
				PreparedStatement pStmt =
						conn.prepareStatement(INSERT_BY_FACILITY_ID_SQL)) {

			pStmt.setString(1, facilityID);
			pStmt.setString(2, closedDay);

			int result = pStmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// JDBCドライバを読み込む
	private void loadDriver() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
	}
}

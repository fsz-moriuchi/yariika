package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DButil;

public class FacilityViewDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_VIEW = "INSERT INTO FacilityView(FACILITY_ID) VALUES(?)";
	private static final String SQL_COUNT_VIEW = "SELECT COUNT(*) AS viewCount FROM FacilityView WHERE FACILITY_ID = ?";

	// 閲覧数追加
	public boolean insertView(String facilityId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_VIEW)) {

			bindFacilityId(stmt, facilityId);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 総閲覧数取得
	public int getViewCount(String facilityId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_VIEW)) {

			bindFacilityId(stmt, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				return readViewCount(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindFacilityId(PreparedStatement stmt, String facilityId) throws Exception {
		stmt.setString(1, facilityId);
	}

	private int readViewCount(ResultSet rs) throws Exception {
		if (rs.next()) {
			return rs.getInt("viewCount");
		}
		return 0;
	}
}
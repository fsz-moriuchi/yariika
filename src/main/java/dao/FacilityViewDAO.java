package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DButil;

public class FacilityViewDAO {

	// 閲覧数追加
	public boolean insertView(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "INSERT INTO FacilityView(FACILITY_ID) VALUES(?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			int result = pStmt.executeUpdate();
			return result == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 総閲覧数取得
	public int getViewCount(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT COUNT(*) AS viewCount "
					+ "FROM FacilityView "
					+ "WHERE FACILITY_ID = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			var rs = pStmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("viewCount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DButil;

public class FacilityClosedDayDAO {
	//予約判定のためだけのメソッド
	public List<String> findByFacilityID(String facilityID) {
		List<String> closedDayList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT closedDay FROM FacilityClosedDay WHERE FACILITY_ID = ? ORDER BY CASE closedDay WHEN 'MONDAY' THEN 1 WHEN 'TUESDAY' THEN 2 WHEN 'WEDNESDAY' THEN 3 WHEN 'THURSDAY' THEN 4 WHEN 'FRIDAY' THEN 5 WHEN 'SATURDAY' THEN 6 WHEN 'SUNDAY' THEN 7 ELSE 8 END";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String closedDay = rs.getString("closedDay");
				closedDayList.add(closedDay);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return closedDayList;
		
	}
	//定休日を設定時に一度削除するためのメソッド
	public boolean deleteByFacilityID(String facilityID) {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "DELETE FROM FacilityClosedDay WHERE FACILITY_ID = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityID);

			pStmt.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//定休日を設定する際にデータを追加するサーブレット
	public boolean insertByFacilityID(String facilityID, String closedDay) {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "INSERT INTO FacilityClosedDay(FACILITY_ID, closedDay) VALUES(?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityID);
			pStmt.setString(2, closedDay);

			pStmt.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}

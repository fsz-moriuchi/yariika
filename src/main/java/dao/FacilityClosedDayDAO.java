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

			String sql = "SELECT closedDay FROM FacilityClosedDay WHERE FACILITY_ID = ?";
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

}

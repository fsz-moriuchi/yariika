package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.FacilityInformation;
import util.DButil;

public class FacilityInfomationDAO {

	// 店舗情報登録
	public boolean insert(FacilityInformation facilityInfo) {
		boolean result = false;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO FacilityInformation "
					+ "(FACILITY_ID, facilityName, tel, address, mail, "
					+ "openTime, closeTime, closedDay) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,facilityInfo.getFacilityId());
			pStmt.setString(2,facilityInfo.getFacilityName());
			pStmt.setString(3,facilityInfo.getTel());
			pStmt.setString(4,facilityInfo.getAddress());
			pStmt.setString(5,facilityInfo.getMail());
			pStmt.setString(6,facilityInfo.getOpenTime());
			pStmt.setString(7,facilityInfo.getCloseTime());
			pStmt.setString(8,facilityInfo.getClosedDay());

			int count = pStmt.executeUpdate();

			result = count > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

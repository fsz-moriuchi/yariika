package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Facility;
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

			pStmt.setString(1, facilityInfo.getFacilityId());
			pStmt.setString(2, facilityInfo.getFacilityName());
			pStmt.setString(3, facilityInfo.getTel());
			pStmt.setString(4, facilityInfo.getAddress());
			pStmt.setString(5, facilityInfo.getMail());
			pStmt.setString(6, facilityInfo.getOpenTime());
			pStmt.setString(7, facilityInfo.getCloseTime());
			pStmt.setString(8, facilityInfo.getClosedDay());

			int count = pStmt.executeUpdate();

			result = count > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public FacilityInformation findByFacilityId(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT * FROM FacilityInformation "
					+ "WHERE FACILITY_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityId);
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return new FacilityInformation(
						rs.getString("FACILITY_ID"),
						rs.getString("facilityName"),
						rs.getString("tel"),
						rs.getString("address"),
						rs.getString("mail"),
						rs.getString("openTime"),
						rs.getString("closeTime"),
						rs.getString("closedDay"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update(FacilityInformation facilityInfo) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = "UPDATE FacilityInformation "
					+ "SET facilityName=?,"
					+ "tel=?,"
					+ "address=?,"
					+ "mail=?,"
					+ "openTime=?,"
					+ "closeTime=?,"
					+ "closedDay=? "
					+ "WHERE FACILITY_ID=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityInfo.getFacilityName());
			pStmt.setString(2, facilityInfo.getTel());
			pStmt.setString(3, facilityInfo.getAddress());
			pStmt.setString(4, facilityInfo.getMail());
			pStmt.setString(5, facilityInfo.getOpenTime());
			pStmt.setString(6, facilityInfo.getCloseTime());
			pStmt.setString(7, facilityInfo.getClosedDay());
			pStmt.setString(8, facilityInfo.getFacilityId());

			return pStmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	public List<Facility> findAllFacility(){
		List<Facility> allFacilityList = new ArrayList<>();

		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
			}

		try (Connection conn = DButil.getConnection()) {

		String sql = "SELECT FACILITY_ID, PASSWORD_HASH FROM FACILITIES ORDER BY FACILITY_ID";
		PreparedStatement pStmt = conn.prepareStatement(sql);

		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
		String facilityId = rs.getString("FACILITY_ID");
		String passwordHash = rs.getString("PASSWORD_HASH");

		Facility facility = new Facility(facilityId, passwordHash);
		allFacilityList.add(facility);
		}

		} catch (Exception e) {
			e.printStackTrace();
		    }
		return allFacilityList;
		
	}
}

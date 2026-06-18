package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Facility;
import model.FacilityLogin;
import util.DButil;

public class FacilitiesDAO {

	public Facility findByLogin(FacilityLogin login) {
		Facility facility = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT FACILITY_ID, PASSWORD_HASH FROM FACILITIES WHERE FACILITY_ID = ? AND PASSWORD_HASH= ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getFacilityId());
			pStmt.setString(2, login.getPasswordHash());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				String facilityId = rs.getString("FACILITY_ID");
				String passwordHash = rs.getString("PASSWORD_HASH");
				facility = new Facility(facilityId, passwordHash);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return facility;
	}

	public boolean registerFacility(Facility facility) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO FACILITIES(FACILITY_ID, PASSWORD_HASH) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facility.getFacilityId());
			pStmt.setString(2, facility.getPasswordHash());

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
	//パスワード変更
		public boolean updateFacilityPassword(Facility facility){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("JBDCドライバを読み込めませんでした");
			}
			try (Connection conn = DButil.getConnection()) {

				String sql = "UPDATE FACILITIES SET PASSWORD_HASH = ? WHERE FACILITY_ID = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				pStmt.setString(1, facility.getPasswordHash());
				pStmt.setString(2, facility.getFacilityId());
				

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

}

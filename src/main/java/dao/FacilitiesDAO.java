package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Facility;
import model.FacilityLogin;
import util.DButil;

public class FacilitiesDAO {

	private static final String SQL_FIND_BY_LOGIN = "SELECT FACILITY_ID, PASSWORD_HASH FROM FACILITIES WHERE FACILITY_ID = ? AND PASSWORD_HASH = ?";
	private static final String SQL_INSERT_FACILITY = "INSERT INTO FACILITIES(FACILITY_ID, PASSWORD_HASH) VALUES(?, ?)";
	private static final String SQL_UPDATE_PASSWORD = "UPDATE FACILITIES SET PASSWORD_HASH = ? WHERE FACILITY_ID = ?";

	public Facility findByLogin(FacilityLogin login) {
		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_LOGIN)) {

			bindLoginParameters(stmt, login);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return toFacility(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public boolean registerFacility(Facility facility) {
		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_FACILITY)) {

			bindFacilityInsertParameters(stmt, facility);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateFacilityPassword(Facility facility) {
		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_PASSWORD)) {

			bindPasswordUpdateParameters(stmt, facility);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void bindLoginParameters(PreparedStatement stmt, FacilityLogin login) throws Exception {
		stmt.setString(1, login.getFacilityId());
		stmt.setString(2, login.getPasswordHash());
	}

	private void bindFacilityInsertParameters(PreparedStatement stmt, Facility facility) throws Exception {
		stmt.setString(1, facility.getFacilityId());
		stmt.setString(2, facility.getPasswordHash());
	}

	private void bindPasswordUpdateParameters(PreparedStatement stmt, Facility facility) throws Exception {
		stmt.setString(1, facility.getPasswordHash());
		stmt.setString(2, facility.getFacilityId());
	}

	private Facility toFacility(ResultSet rs) throws Exception {
		String facilityId = rs.getString("FACILITY_ID");
		String passwordHash = rs.getString("PASSWORD_HASH");
		return new Facility(facilityId, passwordHash);
	}
}
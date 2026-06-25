package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DButil;

public class FacilityClosedDayDAO {
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

	public List<String> findByFacilityID(String facilityID) {
		List<String> closedDayList = new ArrayList<>();

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_FACILITY_ID)) {

			stmt.setString(1, facilityID);

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

	public boolean deleteByFacilityID(Connection connection, String facilityID) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_BY_FACILITY_ID)) {
			stmt.setString(1, facilityID);
			return stmt.executeUpdate() >= 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertByFacilityID(Connection connection, String facilityID, String closedDay) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_INSERT)) {
			stmt.setString(1, facilityID);
			stmt.setString(2, closedDay);
			return stmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean replaceByFacilityID(Connection connection, String facilityID, String[] closedDays) {
		if (!deleteByFacilityID(connection, facilityID)) {
			return false;
		}

		if (closedDays == null || closedDays.length == 0) {
			return true;
		}

		for (String closedDay : closedDays) {
			if (!insertByFacilityID(connection, facilityID, closedDay)) {
				return false;
			}
		}

		return true;
	}
}
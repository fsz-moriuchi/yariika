package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import model.FacilityInformation;

public class FacilityInformationDAO {
	private final String JDBC_URL = "jdbc:sqlserver://localhost\\\\\\\\SQLEXPRESS:61371;databaseName=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	
	public FacilityInformation findByFacilityID(String facilityID){
		FacilityInformation facilityInformation = null;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
		
			String sql = "SELECT facilityInformationID, FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime, closedDay WHERE USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityID);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				int facilityInformationID = rs.getInt("facilityInformationID");
				String FACILITY_ID = rs.getString("FACILITY_ID");
				String facilityName = rs.getString("facilityName");
				String tel = rs.getString("tel");
				String adderss = rs.getString("address");
				String mail = rs.getString("mail");
				LocalTime openTime = rs.getTime("openTime").toLocalTime();
				LocalTime closeTime = rs.getTime("closeTime").toLocalTime();
				String closedDay = rs.getString("closedDay");
				facilityInformation = new FacilityInformation(facilityInformationID, FACILITY_ID, facilityName, tel, adderss, mail, openTime, closeTime, closedDay);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return facilityInformation;
	}
}
	

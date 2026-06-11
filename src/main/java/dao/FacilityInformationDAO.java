package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;

import model.FacilityInformation;
import util.DButil;

public class FacilityInformationDAO {

	public FacilityInformation findByFacilityID(String facilityID) {
		FacilityInformation facilityInformation = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT facilityInformationID, FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime, closedDay FROM FacilityInformation WHERE FACILITY_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityID);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				int facilityInformationID = rs.getInt("facilityInformationID");
				String FACILITY_ID = rs.getString("FACILITY_ID");
				String facilityName = rs.getString("facilityName");
				String tel = rs.getString("tel");
				String address = rs.getString("address");
				String mail = rs.getString("mail");
				LocalTime openTime = rs.getTime("openTime").toLocalTime();
				LocalTime closeTime = rs.getTime("closeTime").toLocalTime();
				String closedDay = rs.getString("closedDay");
				facilityInformation = new FacilityInformation(
				        facilityInformationID,
				        facilityID,
				        facilityName,
				        tel,
				        address,
				        mail,
				        openTime,
				        closeTime,
				        closedDay);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return facilityInformation;
	}
	//お気に入り処理
	public boolean updateFavoritePet(String facilityId, int petID) {

	    try (Connection conn = DButil.getConnection()) {

	        String sql =
	                "UPDATE FacilityInformation " +
	                "SET FAVORITE_PET_ID=? " +
	                "WHERE FACILITY_ID=?";

	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        pStmt.setInt(1, petID);
	        pStmt.setString(2, facilityId);

	        return pStmt.executeUpdate() == 1;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	//お気に入り表示
	public Integer findFavoritePetId(String facilityId) {

	    Integer favoritePetId = null;

	    try (Connection conn = DButil.getConnection()) {

	        String sql =
	                "SELECT FAVORITE_PET_ID " +
	                "FROM FacilityInformation " +
	                "WHERE FACILITY_ID = ?";

	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, facilityId);

	        ResultSet rs = pStmt.executeQuery();

	        if (rs.next()) {
	            favoritePetId = (Integer) rs.getObject("FAVORITE_PET_ID");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return favoritePetId;
	}
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Reserve;
import util.DButil;

public class ReserveDAO {
	public boolean insertReserve(Reserve reserve) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO Reserve(petID, USER_ID, reserveTime) VALUES( ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, reserve.getPetID());
			pStmt.setString(2, reserve.getUserID());
			pStmt.setTimestamp(3, Timestamp.valueOf(reserve.getReserveTime()));

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
	
	public List<LocalTime> findByFacilityAndDate(String facilityID, LocalDate reserveDate) {
	    List<LocalTime> reservedTimeList = new ArrayList<>();

	    try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    } catch (ClassNotFoundException e) {
	        throw new IllegalStateException("JDBCドライバは読み込めませんでした");
	    }

	    try (Connection conn = DButil.getConnection()) {

	        String sql =
	            "SELECT R.reserveTime " +
	            "FROM Reserve R " +
	            "JOIN Pet P ON P.petID = R.petID " +
	            "WHERE P.FACILITY_ID = ? " +
	            "AND CAST(R.reserveTime AS DATE) = ?";

	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, facilityID);
	        pStmt.setDate(2, java.sql.Date.valueOf(reserveDate));

	        ResultSet rs = pStmt.executeQuery();

	        while (rs.next()) {
	            LocalTime reserveTime =
	                    rs.getTimestamp("reserveTime")
	                      .toLocalDateTime()
	                      .toLocalTime();

	            reservedTimeList.add(reserveTime);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return reservedTimeList;
	}
	public boolean existsReserveByPetID(int petID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT COUNT(*) AS CNT FROM Reserve WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, petID);
			
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("CNT") > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}


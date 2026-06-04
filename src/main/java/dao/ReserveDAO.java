package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import model.Reserve;
import util.DButil;

public class ReserveDAO {
	public boolean insertReserve(Reserve reserve) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JBDCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO Reserve(petID, USER_ID, FACILITY_ID, reserveStatus, reserveTime) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, reserve.getPetID());
			pStmt.setString(2, reserve.getUserID());
			pStmt.setString(3, reserve.getFacilityID());
			pStmt.setString(4, reserve.getReserveStatus());
			pStmt.setTimestamp(5, Timestamp.valueOf(reserve.getReserveTime()));

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

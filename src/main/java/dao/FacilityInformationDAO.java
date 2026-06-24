package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Facility;
import model.FacilityInformation;
import util.DButil;

public class FacilityInformationDAO {

	// 店舗情報登録
	public boolean insert(FacilityInformation facilityInfo) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "INSERT INTO FacilityInformation "
					+ "(FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityInfo.getFacilityId());
			pStmt.setString(2, facilityInfo.getFacilityName());
			pStmt.setString(3, facilityInfo.getTel());
			pStmt.setString(4, facilityInfo.getAddress());
			pStmt.setString(5, facilityInfo.getMail());
			pStmt.setTime(6, java.sql.Time.valueOf(facilityInfo.getOpenTime()));
			pStmt.setTime(7, java.sql.Time.valueOf(facilityInfo.getCloseTime()));

			return pStmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 店舗情報取得
	public FacilityInformation findByFacilityId(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT facilityInformationID, FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime "
					+ "FROM FacilityInformation WHERE FACILITY_ID = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				int facilityInformationID = rs.getInt("facilityInformationID");
				String facilityName = rs.getString("facilityName");
				String tel = rs.getString("tel");
				String address = rs.getString("address");
				String mail = rs.getString("mail");
				LocalTime openTime = rs.getTime("openTime").toLocalTime();
				LocalTime closeTime = rs.getTime("closeTime").toLocalTime();

				return new FacilityInformation(
						facilityInformationID,
						facilityId,
						facilityName,
						tel,
						address,
						mail,
						openTime,
						closeTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 店舗情報更新
	public boolean update(FacilityInformation facilityInfo) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "UPDATE FacilityInformation "
					+ "SET facilityName=?, tel=?, address=?, mail=?, openTime=?, closeTime=? "
					+ "WHERE FACILITY_ID=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, facilityInfo.getFacilityName());
			pStmt.setString(2, facilityInfo.getTel());
			pStmt.setString(3, facilityInfo.getAddress());
			pStmt.setString(4, facilityInfo.getMail());
			pStmt.setTime(5, java.sql.Time.valueOf(facilityInfo.getOpenTime()));
			pStmt.setTime(6, java.sql.Time.valueOf(facilityInfo.getCloseTime()));
			pStmt.setString(7, facilityInfo.getFacilityId());

			return pStmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 施設アカウント一覧取得
	public List<Facility> findAllFacility() {
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
				allFacilityList.add(new Facility(facilityId, passwordHash));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allFacilityList;
	}

	// おすすめペットID更新
	public boolean updateFavoritePet(String facilityId, int petID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "UPDATE FacilityInformation SET FAVORITE_PET_ID=? WHERE FACILITY_ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, petID);
			pStmt.setString(2, facilityId);

			return pStmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// おすすめペットID取得
	public Integer findFavoritePetId(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT FAVORITE_PET_ID FROM FacilityInformation WHERE FACILITY_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return (Integer) rs.getObject("FAVORITE_PET_ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
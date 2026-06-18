package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.FacilityHomeView;
import model.FacilityPetView;
import model.PopularPetView;
import util.DButil;

public class FacilityHomeDAO {

	//	店舗情報取得
	public FacilityHomeView showFacilityInfo(String facilityId) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = """
					SELECT
					    FACILITY_ID,
					    facilityName,
					    address,
					    tel,
					    mail,
					    openTime,
					    closeTime
					FROM FacilityInformation
					WHERE FACILITY_ID = ?
					""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return new FacilityHomeView(
						rs.getString("FACILITY_ID"),
						rs.getString("facilityName"),
						rs.getString("address"),
						rs.getString("tel"),
						rs.getString("mail"),
						rs.getString("openTime"),
						rs.getString("closeTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//	所属ペット一覧
	public List<FacilityPetView> showPetList(String facilityId) {
		List<FacilityPetView> petList = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = """
					SELECT
					    P.petID,
					    PI.name,
					    PI.gender,
					    PI.age,
					    PI.price,
					    PI.imagePath
					FROM Pet P
					JOIN PetInformation PI
					    ON P.petID = PI.petID
					WHERE P.FACILITY_ID = ?
					ORDER BY P.petID
					""";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				FacilityPetView pet = new FacilityPetView(
						rs.getInt("petID"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getInt("price"),
						rs.getString("imagePath"));

				petList.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return petList;
	}

	//人気ランキング
	public List<PopularPetView> showPopularRanking(String facilityId) {

		List<PopularPetView> rankingList = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = """
					SELECT
					    P.petID,
					    PI.name,
					    PI.imagePath,
					    COUNT(F.favoriteID) AS favoriteCount
					FROM Pet P
					JOIN PetInformation PI
					    ON P.petID = PI.petID
					LEFT JOIN Favorite F
					    ON P.petID = F.petID
					WHERE P.FACILITY_ID = ?
					GROUP BY
					    P.petID,
					    PI.name,
					    PI.imagePath
					ORDER BY favoriteCount DESC
					""";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				PopularPetView pet = new PopularPetView(
						rs.getInt("petID"),
						rs.getString("name"),
						rs.getString("imagePath"),
						rs.getInt("favoriteCount"));
				rankingList.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rankingList;
	}

	//	店舗おすすめペット
	public FacilityPetView showFavoritePet(String facilityId) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql = """
					SELECT
					    P.petID,
					    PI.name,
					    PI.gender,
					    PI.age,
					    PI.price,
					    PI.imagePath
					FROM FacilityInformation FI
					JOIN Pet P
					    ON FI.FAVORITE_PET_ID = P.petID
					JOIN PetInformation PI
					    ON P.petID = PI.petID
					WHERE FI.FACILITY_ID = ?
					""";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				return new FacilityPetView(
						rs.getInt("petID"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getInt("price"),
						rs.getString("imagePath"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//定休日取得
	public List<String> getClosedDays(String facilityId) {

		List<String> closedDayList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = """
					SELECT closedDay
					FROM FacilityClosedDay
					WHERE FACILITY_ID = ?
					""";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				closedDayList.add(rs.getString("closedDay"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return closedDayList;
	}
}

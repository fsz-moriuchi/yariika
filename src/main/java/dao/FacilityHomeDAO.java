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

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static final String SQL_FACILITY_INFO = """
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

	private static final String SQL_PET_LIST = """
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

	private static final String SQL_POPULAR_RANKING = """
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

	private static final String SQL_FAVORITE_PET = """
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

	private static final String SQL_CLOSED_DAYS = """
			SELECT closedDay
			FROM FacilityClosedDay
			WHERE FACILITY_ID = ?
			""";

	// 店舗情報取得
	public FacilityHomeView showFacilityInfo(String facilityId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FACILITY_INFO)) {

			bindFacilityId(stmt, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return toFacilityHomeView(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 所属ペット一覧
	public List<FacilityPetView> showPetList(String facilityId) {
		List<FacilityPetView> petList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_PET_LIST)) {

			bindFacilityId(stmt, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					petList.add(toFacilityPetView(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return petList;
	}

	// 人気ランキング
	public List<PopularPetView> showPopularRanking(String facilityId) {
		List<PopularPetView> rankingList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_POPULAR_RANKING)) {

			bindFacilityId(stmt, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					rankingList.add(toPopularPetView(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rankingList;
	}

	// 店舗おすすめペット
	public FacilityPetView showFavoritePet(String facilityId) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FAVORITE_PET)) {

			bindFacilityId(stmt, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return toFacilityPetView(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 定休日取得
	public List<String> getClosedDays(String facilityId) {
		List<String> closedDayList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_CLOSED_DAYS)) {

			bindFacilityId(stmt, facilityId);

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

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindFacilityId(PreparedStatement stmt, String facilityId) throws Exception {
		stmt.setString(1, facilityId);
	}

	private FacilityHomeView toFacilityHomeView(ResultSet rs) throws Exception {
		return new FacilityHomeView(
				rs.getString("FACILITY_ID"),
				rs.getString("facilityName"),
				rs.getString("address"),
				rs.getString("tel"),
				rs.getString("mail"),
				rs.getString("openTime"),
				rs.getString("closeTime"));
	}

	private FacilityPetView toFacilityPetView(ResultSet rs) throws Exception {
		return new FacilityPetView(
				rs.getInt("petID"),
				rs.getString("name"),
				rs.getString("gender"),
				rs.getInt("age"),
				rs.getInt("price"),
				rs.getString("imagePath"));
	}

	private PopularPetView toPopularPetView(ResultSet rs) throws Exception {
		return new PopularPetView(
				rs.getInt("petID"),
				rs.getString("name"),
				rs.getString("imagePath"),
				rs.getInt("favoriteCount"));
	}
}
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

	public FacilityHomeView showFacilityInfo(String facilityId) {
		try (Connection connection = DButil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL_FACILITY_INFO)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return toFacilityHomeView(resultSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<FacilityPetView> showPetList(String facilityId) {
		return fetchFacilityPetViews(facilityId, SQL_PET_LIST);
	}

	public List<PopularPetView> showPopularRanking(String facilityId) {
		List<PopularPetView> rankingList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL_POPULAR_RANKING)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					rankingList.add(toPopularPetView(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rankingList;
	}

	public FacilityPetView showFavoritePet(String facilityId) {
		try (Connection connection = DButil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL_FAVORITE_PET)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return toFacilityPetView(resultSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getClosedDays(String facilityId) {
		List<String> closedDayList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL_CLOSED_DAYS)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					closedDayList.add(resultSet.getString("closedDay"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return closedDayList;
	}

	private <T> List<T> executeQueryList(String facilityId, String sql, RowMapper<T> mapper) {
		List<T> list = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					list.add(mapper.map(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<FacilityPetView> fetchFacilityPetViews(String facilityId, String sql) {
		return executeQueryList(facilityId, sql, this::toFacilityPetView);
	}

	private FacilityHomeView toFacilityHomeView(ResultSet resultSet) throws Exception {
		return new FacilityHomeView(
				resultSet.getString("FACILITY_ID"),
				resultSet.getString("facilityName"),
				resultSet.getString("address"),
				resultSet.getString("tel"),
				resultSet.getString("mail"),
				resultSet.getString("openTime"),
				resultSet.getString("closeTime"));
	}

	private FacilityPetView toFacilityPetView(ResultSet resultSet) throws Exception {
		return new FacilityPetView(
				resultSet.getInt("petID"),
				resultSet.getString("name"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getInt("price"),
				resultSet.getString("imagePath"));
	}

	private PopularPetView toPopularPetView(ResultSet resultSet) throws Exception {
		return new PopularPetView(
				resultSet.getInt("petID"),
				resultSet.getString("name"),
				resultSet.getString("imagePath"),
				resultSet.getInt("favoriteCount"));
	}

	@FunctionalInterface
	private interface RowMapper<T> {
		T map(ResultSet resultSet) throws Exception;
	}
}
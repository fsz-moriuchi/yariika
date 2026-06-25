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

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	private static final String SQL_INSERT = """
			INSERT INTO FacilityInformation
			(FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime)
			VALUES (?, ?, ?, ?, ?, ?, ?)
			""";

	private static final String SQL_FIND_BY_FACILITY_ID = """
			SELECT facilityInformationID, FACILITY_ID, facilityName, tel, address, mail, openTime, closeTime
			FROM FacilityInformation
			WHERE FACILITY_ID = ?
			""";

	private static final String SQL_UPDATE = """
			UPDATE FacilityInformation
			SET facilityName=?, tel=?, address=?, mail=?, openTime=?, closeTime=?
			WHERE FACILITY_ID=?
			""";

	private static final String SQL_FIND_ALL_FACILITY = """
			SELECT FACILITY_ID, PASSWORD_HASH
			FROM FACILITIES
			ORDER BY FACILITY_ID
			""";

	private static final String SQL_UPDATE_FAVORITE_PET = """
			UPDATE FacilityInformation
			SET FAVORITE_PET_ID=?
			WHERE FACILITY_ID=?
			""";

	private static final String SQL_FIND_FAVORITE_PET_ID = """
			SELECT FAVORITE_PET_ID
			FROM FacilityInformation
			WHERE FACILITY_ID = ?
			""";

	public boolean insert(FacilityInformation facilityInformation) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

			bindInsertParameters(statement, facilityInformation);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public FacilityInformation findByFacilityId(String facilityId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_FACILITY_ID)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapToFacilityInformation(facilityId, resultSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean update(FacilityInformation facilityInformation) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

			bindUpdateParameters(statement, facilityInformation);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Connection connection, FacilityInformation facilityInformation) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
			bindUpdateParameters(statement, facilityInformation);
			return statement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Facility> findAllFacility() {
		List<Facility> facilities = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_FACILITY);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				facilities.add(mapToFacility(resultSet));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return facilities;
	}

	public boolean updateFavoritePet(String facilityId, int favoritePetId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FAVORITE_PET)) {

			statement.setInt(1, favoritePetId);
			statement.setString(2, facilityId);

			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Integer findFavoritePetId(String facilityId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_FAVORITE_PET_ID)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return (Integer) resultSet.getObject("FAVORITE_PET_ID");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void bindInsertParameters(PreparedStatement statement, FacilityInformation facilityInformation)
			throws Exception {
		statement.setString(1, facilityInformation.getFacilityId());
		statement.setString(2, facilityInformation.getFacilityName());
		statement.setString(3, facilityInformation.getTel());
		statement.setString(4, facilityInformation.getAddress());
		statement.setString(5, facilityInformation.getMail());
		statement.setTime(6, java.sql.Time.valueOf(facilityInformation.getOpenTime()));
		statement.setTime(7, java.sql.Time.valueOf(facilityInformation.getCloseTime()));
	}

	private void bindUpdateParameters(PreparedStatement statement, FacilityInformation facilityInformation)
			throws Exception {
		statement.setString(1, facilityInformation.getFacilityName());
		statement.setString(2, facilityInformation.getTel());
		statement.setString(3, facilityInformation.getAddress());
		statement.setString(4, facilityInformation.getMail());
		statement.setTime(5, java.sql.Time.valueOf(facilityInformation.getOpenTime()));
		statement.setTime(6, java.sql.Time.valueOf(facilityInformation.getCloseTime()));
		statement.setString(7, facilityInformation.getFacilityId());
	}

	private FacilityInformation mapToFacilityInformation(String facilityId, ResultSet resultSet) throws Exception {
		int facilityInformationId = resultSet.getInt("facilityInformationID");
		String facilityName = resultSet.getString("facilityName");
		String tel = resultSet.getString("tel");
		String address = resultSet.getString("address");
		String mail = resultSet.getString("mail");
		LocalTime openTime = resultSet.getTime("openTime").toLocalTime();
		LocalTime closeTime = resultSet.getTime("closeTime").toLocalTime();

		return new FacilityInformation(
				facilityInformationId,
				facilityId,
				facilityName,
				tel,
				address,
				mail,
				openTime,
				closeTime);
	}

	private Facility mapToFacility(ResultSet resultSet) throws Exception {
		return new Facility(
				resultSet.getString("FACILITY_ID"),
				resultSet.getString("PASSWORD_HASH"));
	}
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DButil;

public class FacilityViewDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_VIEW = "INSERT INTO FacilityView(FACILITY_ID) VALUES(?)";
	private static final String SQL_COUNT_VIEW = "SELECT COUNT(*) AS viewCount FROM FacilityView WHERE FACILITY_ID = ?";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public boolean insertView(String facilityId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_VIEW)) {

			bindFacilityId(statement, facilityId);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getViewCount(String facilityId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_COUNT_VIEW)) {

			bindFacilityId(statement, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				return extractViewCount(resultSet);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private void bindFacilityId(PreparedStatement statement, String facilityId) throws Exception {
		statement.setString(1, facilityId);
	}

	private int extractViewCount(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			return resultSet.getInt("viewCount");
		}
		return 0;
	}
}
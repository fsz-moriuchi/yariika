package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.FavoriteView;
import util.DButil;

public class FavoriteDAO {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String SQL_INSERT_FAVORITE = "INSERT INTO Favorite(USER_ID, petID) VALUES(?, ?)";
	private static final String SQL_IS_FAVORITE = "SELECT * FROM Favorite WHERE USER_ID=? AND petID=?";
	private static final String SQL_DELETE_FAVORITE = "DELETE FROM Favorite WHERE USER_ID=? AND petID=?";
	private static final String SQL_SHOW_FAVORITES = """
			SELECT
			    PI.petID,
			    PI.name,
			    PI.gender,
			    PI.age,
			    PI.price,
			    PI.imagePath
			FROM Favorite F
			JOIN PetInformation PI
			    ON F.petID = PI.petID
			WHERE F.USER_ID = ?
			ORDER BY PI.petID
			""";
	private static final String SQL_COUNT_FAVORITES = "SELECT COUNT(*) AS cnt FROM Favorite WHERE petID=?";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public boolean insertFavorite(String userId, int petId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FAVORITE)) {

			bindUserIdAndPetId(statement, userId, petId);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isFavorite(String userId, int petId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_IS_FAVORITE)) {

			bindUserIdAndPetId(statement, userId, petId);

			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteFavorite(String userId, int petId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FAVORITE)) {

			bindUserIdAndPetId(statement, userId, petId);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<FavoriteView> showFavoriteList(String userId) {
		List<FavoriteView> favoriteList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_FAVORITES)) {

			statement.setString(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					favoriteList.add(toFavoriteView(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return favoriteList;
	}

	public int countFavorite(int petId) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_COUNT_FAVORITES)) {

			statement.setInt(1, petId);

			try (ResultSet resultSet = statement.executeQuery()) {
				return readCount(resultSet);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private void bindUserIdAndPetId(PreparedStatement statement, String userId, int petId) throws Exception {
		statement.setString(1, userId);
		statement.setInt(2, petId);
	}

	private FavoriteView toFavoriteView(ResultSet resultSet) throws Exception {
		return new FavoriteView(
				resultSet.getInt("petID"),
				resultSet.getString("name"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getInt("price"),
				resultSet.getString("imagePath"));
	}

	private int readCount(ResultSet resultSet) throws Exception {
		if (resultSet.next()) {
			return resultSet.getInt("cnt");
		}
		return 0;
	}
}
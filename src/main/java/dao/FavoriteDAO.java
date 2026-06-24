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

	// お気に入り登録
	public boolean insertFavorite(String userId, int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_FAVORITE)) {

			bindUserIdAndPetId(stmt, userId, petID);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// お気に入り済みか判定
	public boolean isFavorite(String userId, int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_IS_FAVORITE)) {

			bindUserIdAndPetId(stmt, userId, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// お気に入り解除
	public boolean deleteFavorite(String userId, int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_FAVORITE)) {

			bindUserIdAndPetId(stmt, userId, petID);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// お気に入り一覧表示
	public List<FavoriteView> showFavoriteList(String userId) {
		List<FavoriteView> favoriteList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_FAVORITES)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					favoriteList.add(toFavoriteView(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return favoriteList;
	}

	// お気に入りカウント
	public int countFavorite(int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_FAVORITES)) {

			stmt.setInt(1, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("cnt");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindUserIdAndPetId(PreparedStatement stmt, String userId, int petID) throws Exception {
		stmt.setString(1, userId);
		stmt.setInt(2, petID);
	}

	private FavoriteView toFavoriteView(ResultSet rs) throws Exception {
		return new FavoriteView(
				rs.getInt("petID"),
				rs.getString("name"),
				rs.getString("gender"),
				rs.getInt("age"),
				rs.getInt("price"),
				rs.getString("imagePath"));
	}
}
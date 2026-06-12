package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.FavoriteView;
import util.DButil;

public class FavoriteDAO {

	// お気に入り登録
	public boolean insertFavorite(String userId, int petID) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO Favorite(USER_ID, petID) VALUES(?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userId);
			pStmt.setInt(2, petID);

			int result = pStmt.executeUpdate();

			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//お気に入り済みか判定
	public boolean isFavorite(String userId, int petID) {

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT * FROM Favorite WHERE USER_ID=? AND petID=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userId);
			pStmt.setInt(2, petID);

			ResultSet rs = pStmt.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//お気に入り解除
	public boolean deleteFavorite(String userId, int petID) {

		try (Connection conn = DButil.getConnection()) {

			String sql = "DELETE FROM Favorite WHERE USER_ID=? AND petID=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userId);
			pStmt.setInt(2, petID);

			int result = pStmt.executeUpdate();

			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//	お気に入り一覧表示
	public List<FavoriteView> showFavoriteList(String userId) {
		List<FavoriteView> favoriteList = new ArrayList<>();
		try (Connection conn = DButil.getConnection()) {
			String sql = """
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
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				FavoriteView favorite = new FavoriteView(
						rs.getInt("petID"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getInt("price"),
						rs.getString("imagePath"));
				favoriteList.add(favorite);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return favoriteList;
	}

}
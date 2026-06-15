package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FavoritePet;
import model.Pet;
import model.PetDetail;
import model.PetInformation;
import model.PetInformationView;
import model.PetSurvey;
import util.DButil;

public class PetListDAO {

	//（店舗）create 新規ペット作成
	public int createPet(Pet pet) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO Pet(FACILITY_ID,CATEGORY_ID) OUTPUT INSERTED.petID VALUES(?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, pet.getFacilityID());
			pStmt.setInt(2, pet.getCategoryID());

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//新規インフォメーショ
	public boolean createPetInformation(PetInformation petInformation) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO PetInformation( petID, name, gender, age, color, pet_size, vaccine, price, commentText, imagePath) VALUES(?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petInformation.getPetID());
			pStmt.setString(2, petInformation.getName());
			pStmt.setString(3, petInformation.getGender());
			pStmt.setInt(4, petInformation.getAge());
			pStmt.setString(5, petInformation.getColor());
			pStmt.setString(6, petInformation.getPet_size());
			pStmt.setString(7, petInformation.getVaccine());
			pStmt.setInt(8, petInformation.getPrice());
			pStmt.setString(9, petInformation.getCommentText());
			pStmt.setString(10, petInformation.getImagePath());

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

	//新規アンケート
	public boolean createPetSurvey(PetSurvey petSurvey) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "INSERT INTO PetSurvey(petID,questionID,surveyChoiceID) VALUES(?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, petSurvey.getPetID());
			pStmt.setInt(2, petSurvey.getQuestionID());
			pStmt.setInt(3, petSurvey.getSurveyChoiceID());

			int result = pStmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	//（店舗）update ペット修正
	public boolean updatePet(Pet pet) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE Pet SET FACILITY_ID=?,CATEGORY_ID=? WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, pet.getFacilityID());
			pStmt.setInt(2, pet.getCategoryID());
			pStmt.setInt(3, pet.getPetID());

			int result = pStmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	//ペット情報更新
	public boolean updatePetInformation(PetInformation petInformation) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE PetInformation "
					+ "SET name=?, gender=?, age=?, color=?, pet_size=?, vaccine=?, "
					+ "price=?, commentText=?, imagePath=? "
					+ "WHERE petID=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, petInformation.getName());
			pStmt.setString(2, petInformation.getGender());
			pStmt.setInt(3, petInformation.getAge());
			pStmt.setString(4, petInformation.getColor());
			pStmt.setString(5, petInformation.getPet_size());
			pStmt.setString(6, petInformation.getVaccine());
			pStmt.setInt(7, petInformation.getPrice());
			pStmt.setString(8, petInformation.getCommentText());
			pStmt.setString(9, petInformation.getImagePath());
			pStmt.setInt(10, petInformation.getPetID());

			int result = pStmt.executeUpdate();

			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//アンケート更新
	public boolean updatePetSurvey(int petID, int questionID, int surveyChoiceID) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String updateSql = "UPDATE PetSurvey SET SurveyChoiceID = ? WHERE petID = ? AND QuestionID = ?";
			PreparedStatement updatepStmt = conn.prepareStatement(updateSql);

			updatepStmt.setInt(1, surveyChoiceID);
			updatepStmt.setInt(2, petID);
			updatepStmt.setInt(3, questionID);
			int updateResult = updatepStmt.executeUpdate();

			//既存問題があれば更新成功
			if (updateResult == 1) {
				return true;
			}

			//アンケート新規問題があるとき　insert new question
			String insertSql = "INSERT INTO PetSurvey (petID, QuestionID, SurveyChoiceID) VALUES(?, ?, ?)";
			PreparedStatement insertpStmt = conn.prepareStatement(insertSql);

			insertpStmt.setInt(1, petID);
			insertpStmt.setInt(2, questionID);
			insertpStmt.setInt(3, surveyChoiceID);

			int insertResult = insertpStmt.executeUpdate();

			return insertResult == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//（店舗）delete ペット情報削除
	public boolean deletePet(int petID) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			// ★おすすめ解除
			String sql1 = "UPDATE FacilityInformation " +
					"SET FAVORITE_PET_ID = NULL " +
					"WHERE FAVORITE_PET_ID = ?";

			PreparedStatement pStmt1 = conn.prepareStatement(sql1);
			pStmt1.setInt(1, petID);
			pStmt1.executeUpdate();

			// ペット削除
			String sql2 = "DELETE FROM Pet WHERE petID = ?";

			PreparedStatement pStmt2 = conn.prepareStatement(sql2);
			pStmt2.setInt(1, petID);

			int result = pStmt2.executeUpdate();

			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//（顧客＆店舗）show ペット
	public List<PetInformationView> showList() {
		List<PetInformationView> petList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, PI.gender, PI.age, PI.price FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID ORDER BY P.petID";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int petID = rs.getInt("petID");
				String facilityId = rs.getString("FACILITY_ID");
				int categoryId = rs.getInt("CATEGORY_ID");

				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int price = rs.getInt("price");
				PetInformationView petInformationView = new PetInformationView(petID, facilityId, categoryId, gender,
						age, price);
				petList.add(petInformationView);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return petList;

	}

	//ペット詳細表示
	public PetDetail showPetDetail(int petID) {
		PetDetail petDetail = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT " +
					"P.petID, " +
					"P.CATEGORY_ID, " +
					"P.FACILITY_ID, " +
					"C.CATEGORY_NAME, " +
					"PI.petInformationID, " +
					"PI.name, " +
					"PI.gender, " +
					"PI.age, " +
					"PI.color, " +
					"PI.pet_size, " +
					"PI.vaccine, " +
					"PI.price, " +
					"PI.commentText, " +
					"PI.imagePath, " +
					"FI.facilityName, " +
					"FI.address, " +
					"FI.tel " +
					"FROM Pet P " +
					"JOIN PetInformation PI ON P.petID = PI.petID " +
					"JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID " +
					"JOIN FacilityInformation FI ON P.FACILITY_ID = FI.FACILITY_ID " +
					"WHERE P.petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int categoryId = rs.getInt("CATEGORY_ID");
				String facilityID = rs.getString("FACILITY_ID");
				String categoryName = rs.getString("CATEGORY_NAME");

				int petInformationID = rs.getInt("petInformationID");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String color = rs.getString("color");
				String pet_size = rs.getString("pet_size");
				String vaccine = rs.getString("vaccine");
				int price = rs.getInt("price");
				String commentText = rs.getString("commentText");

				String imagePath = rs.getString("imagePath");

				String facilityName = rs.getString("facilityName");
				String address = rs.getString("address");
				String tel = rs.getString("tel");

				petDetail = new PetDetail(
						petID,
						categoryId,
						facilityID,
						categoryName,
						petInformationID,
						name,
						gender,
						age,
						color,
						pet_size,
						vaccine,
						price,
						commentText,
						imagePath,
						facilityName,
						address,
						tel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return petDetail;
	}

	//ペットアンケート表示
	public List<PetSurvey> showPetSurvey(int petID) {
		List<PetSurvey> petSurveyList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT * FROM PetSurvey WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petID);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int questionID = rs.getInt("questionID");
				int surveyChoiceID = rs.getInt("surveyChoiceID");
				PetSurvey petSurvey = new PetSurvey(petID, questionID, surveyChoiceID);
				petSurveyList.add(petSurvey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return petSurveyList;
	}

	//お気に入りペット
	public List<PetInformationView> showListByFacility(String facilityId) {
		List<PetInformationView> facilityList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, " +
					"PI.gender, PI.age, PI.price, PI.imagePath " +
					"FROM Pet P " +
					"JOIN PetInformation PI ON P.petID = PI.petID " +
					"WHERE P.FACILITY_ID = ? " +
					"ORDER BY P.petID";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, facilityId);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int petID = rs.getInt("petID");
				String facilityId1 = rs.getString("FACILITY_ID");
				int categoryId = rs.getInt("CATEGORY_ID");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int price = rs.getInt("price");
				String imagePath = rs.getString("imagePath");
				PetInformationView petInformationView = new PetInformationView(petID, facilityId1, categoryId, gender,
						age, price, imagePath);
				facilityList.add(petInformationView);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return facilityList;

	}

	//お気に入りペット表示
	public List<FavoritePet> showFavoritePet() {

		List<FavoritePet> list = new ArrayList<>();

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT FI.facilityName,"
					+ " P.petID,"
					+ " PI.name,"
					+ " PI.gender,"
					+ " PI.age,"
					+ " PI.price,"
					+ " PI.imagePath "
					+ "FROM FacilityInformation FI "
					+ "JOIN Pet P "
					+ "ON FI.FAVORITE_PET_ID = P.petID "
					+ "JOIN PetInformation PI "
					+ "ON P.petID = PI.petID "
					+ "WHERE FI.FAVORITE_PET_ID IS NOT NULL";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				FavoritePet pet = new FavoritePet(
						rs.getString("facilityName"),
						rs.getInt("petID"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getInt("price"),
						rs.getString("imagePath"));
				list.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//顧客とペットのMatchRate取得
	public Map<Integer, Integer> getAllMatchRate(String userId) {
		Map<Integer, Integer> allMatchRateMap = new HashMap<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT P.petID, COUNT(US.QuestionID) * 10 AS matchRate " +
					"FROM Pet P " +
					"JOIN PetSurvey PS " +
					"ON P.petID = PS.petID " +
					"AND PS.QuestionID BETWEEN 1 AND 10 " +
					"LEFT JOIN UserSurvey US " +
					"ON US.QuestionID = PS.QuestionID " +
					"AND US.SurveyChoiceID = PS.SurveyChoiceID " +
					"AND US.USER_ID = ? " +
					"AND US.QuestionID BETWEEN 1 AND 10 " +
					"GROUP BY P.petID";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int petID = rs.getInt("petID");
				int matchRate = rs.getInt("matchRate");

				allMatchRateMap.put(petID, matchRate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allMatchRateMap;

	}
	//条件検索
	public List<PetDetail> searchAllPet(
			String categoryId,
			String gender,
			String[] colorArray,
			String pet_size,
			String ageRange,
			String priceRange) {

		List<PetDetail> searchPetList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {
			String sql =
					"SELECT " +
					"P.petID, " +
					"P.CATEGORY_ID, " +
					"P.FACILITY_ID, " +
					"C.CATEGORY_NAME, " +
					"PI.petInformationID, " +
					"PI.name, " +
					"PI.gender, " +
					"PI.age, " +
					"PI.color, " +
					"PI.pet_size, " +
					"PI.vaccine, " +
					"PI.price, " +
					"PI.commentText, " +
					"PI.imagePath, " +
					"FI.facilityName, " +
					"FI.address, " +
					"FI.tel " +
					"FROM Pet P " +
					"JOIN PetInformation PI ON P.petID = PI.petID " +
					"JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID " +
					"JOIN FacilityInformation FI ON P.FACILITY_ID = FI.FACILITY_ID " +
					"WHERE 1 = 1 ";
			//WHERE 1 = 1 >> sql文の後ろに 'AND'で追加できる
			
			//選択した条件の保存List：selectedList
			List<Object> selectedList = new ArrayList<>();

			// カテゴリー
			if(categoryId != null && !categoryId.isEmpty()) {
				sql += "AND P.CATEGORY_ID = ? ";
				selectedList.add(Integer.parseInt(categoryId));
			}

			// 性別
			if(gender != null && !gender.isEmpty()) {
				sql += "AND PI.gender = ? ";
				selectedList.add(gender);
			}

			// 色・柄：複数選択可能
			if(colorArray != null && colorArray.length > 0) {
				sql += "AND (";
				for(int i = 0; i < colorArray.length; i++) {
					sql += "PI.color LIKE ? ";
					selectedList.add("%" + colorArray[i] + "%");

					if(i < colorArray.length - 1) {
						sql += "OR ";
					}
				}
				sql += ") ";
			}

			// サイズ
			if(pet_size != null && !pet_size.isEmpty()) {
				sql += "AND PI.pet_size = ? ";
				selectedList.add(pet_size);
			}

			// 年齢
			if(ageRange != null && !ageRange.isEmpty()) {
				if("age0".equals(ageRange)) {
					sql += "AND PI.age = 0 ";
				} else if("age1to3".equals(ageRange)) {
					sql += "AND PI.age BETWEEN 1 AND 3 ";
				} else if("age4up".equals(ageRange)) {
					sql += "AND PI.age >= 4 ";
				}
			}

			// 価格
			if(priceRange != null && !priceRange.isEmpty()) {
				if("price0to100000".equals(priceRange)) {
					sql += "AND PI.price BETWEEN 0 AND 100000 ";
				} else if("price100001to300000".equals(priceRange)) {
					sql += "AND PI.price BETWEEN 100001 AND 300000 ";
				} else if("price300001to500000".equals(priceRange)) {
					sql += "AND PI.price BETWEEN 300001 AND 500000 ";
				} else if("price500001up".equals(priceRange)) {
					sql += "AND PI.price >= 500001 ";
				}
			}

			sql += "ORDER BY P.petID";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			//Loop：selectedListで保存した値を一つ一つSQL文の'?'に入れる
			for(int i = 0; i < selectedList.size(); i++) {
				Object o = selectedList.get(i);
				//中身を一つ一つチェック:Integer/String
				//Integer：
				if(o instanceof Integer) {
					pStmt.setInt(i + 1, (Integer)o);
				} else {
					//String：
					pStmt.setString(i + 1, (String)o);
				}
			}

			ResultSet rs = pStmt.executeQuery();

			while(rs.next()) {
				PetDetail pet = new PetDetail(
						rs.getInt("petID"),
						rs.getInt("CATEGORY_ID"),
						rs.getString("FACILITY_ID"),
						rs.getString("CATEGORY_NAME"),
						rs.getInt("petInformationID"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getString("color"),
						rs.getString("pet_size"),
						rs.getString("vaccine"),
						rs.getInt("price"),
						rs.getString("commentText"),
						rs.getString("imagePath"),
						rs.getString("facilityName"),
						rs.getString("address"),
						rs.getString("tel"));

				searchPetList.add(pet);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return searchPetList;
		}
	
}

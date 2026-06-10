package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

			String sql = "INSERT INTO PetInformation( petID, name, gender, age, color, pet_size, vaccine, price, commentText) VALUES(?,?,?,?,?,?,?,?,?)";
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

	//（店舗）update ペット情報修正
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

	public boolean updatePetInformation(PetInformation petInformation) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE PetInformation SET name=?,gender=?,age=?,color=?,pet_size=?,vaccine=?,price=?,commentText=? WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, petInformation.getName());
			pStmt.setString(2, petInformation.getGender());
			pStmt.setInt(3, petInformation.getAge());
			pStmt.setString(4, petInformation.getColor());
			pStmt.setString(5, petInformation.getPet_size());
			pStmt.setString(6, petInformation.getVaccine());
			pStmt.setInt(7, petInformation.getPrice());
			pStmt.setString(8, petInformation.getCommentText());
			pStmt.setInt(9, petInformation.getPetID());

			int result = pStmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetSurvey(int petID, int questionID, int surveyChoiceID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE PetSurvey SET surveyChoiceID = ? WHERE petID = ? AND questionID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(2, petID);
			pStmt.setInt(3, questionID);
			pStmt.setInt(1, surveyChoiceID);

			int result = pStmt.executeUpdate();
			return result == 1;

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

			String sql = "DELETE FROM Pet WHERE petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petID);
			int result = pStmt.executeUpdate();

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

	public PetDetail showPetDetail(int petID) {
		PetDetail petDetail = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, C.CATEGORY_NAME, PI.petInformationID, PI.name, PI.gender, PI.age, PI.color, PI.pet_size, PI.vaccine, PI.price, PI.commentText FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID WHERE P.petID = ?";

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
						commentText);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return petDetail;
	}

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

	//お気に入りペット[追加点（森内）]
	public List<PetInformationView> showListByFacility(String facilityId) {
		List<PetInformationView> facilityList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {

			String sql = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, PI.gender, PI.age, PI.price FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID WHERE P.FACILITY_ID = ? ORDER BY P.petID";
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
				PetInformationView petInformationView = new PetInformationView(petID, facilityId1, categoryId, gender,
						age, price);
				facilityList.add(petInformationView);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return facilityList;

	}

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

}

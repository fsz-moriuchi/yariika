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

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private static final String SQL_CREATE_PET = "INSERT INTO Pet(FACILITY_ID, CATEGORY_ID) OUTPUT INSERTED.petID VALUES(?, ?)";
	private static final String SQL_CREATE_PET_INFORMATION = "INSERT INTO PetInformation(petID, name, gender, age, color, pet_size, vaccine, price, commentText, imagePath) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_CREATE_PET_SURVEY = "INSERT INTO PetSurvey(petID, questionID, surveyChoiceID) VALUES(?,?,?)";
	private static final String SQL_UPDATE_PET = "UPDATE Pet SET FACILITY_ID=?, CATEGORY_ID=? WHERE petID = ?";
	private static final String SQL_UPDATE_PET_INFORMATION = "UPDATE PetInformation "
			+ "SET name=?, gender=?, age=?, color=?, pet_size=?, vaccine=?, "
			+ "price=?, commentText=?, imagePath=? "
			+ "WHERE petID=?";
	private static final String SQL_UPDATE_PET_SURVEY = "UPDATE PetSurvey SET SurveyChoiceID = ? WHERE petID = ? AND QuestionID = ?";
	private static final String SQL_INSERT_PET_SURVEY = "INSERT INTO PetSurvey(petID, QuestionID, SurveyChoiceID) VALUES(?, ?, ?)";
	private static final String SQL_DELETE_PET = "DELETE FROM Pet WHERE petID = ?";
	private static final String SQL_CLEAR_FAVORITE_PET = "UPDATE FacilityInformation SET FAVORITE_PET_ID = NULL WHERE FAVORITE_PET_ID = ?";
	private static final String SQL_SHOW_PETS = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, PI.gender, PI.age, PI.price "
			+ "FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID "
			+ "ORDER BY P.petID";
	private static final String SQL_SHOW_PET_DETAIL = "SELECT "
			+ "P.petID, P.CATEGORY_ID, P.FACILITY_ID, C.CATEGORY_NAME, "
			+ "PI.petInformationID, PI.name, PI.gender, PI.age, PI.color, PI.pet_size, PI.vaccine, "
			+ "PI.price, PI.commentText, PI.imagePath, FI.facilityName, FI.address, FI.tel "
			+ "FROM Pet P "
			+ "JOIN PetInformation PI ON P.petID = PI.petID "
			+ "JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID "
			+ "JOIN FacilityInformation FI ON P.FACILITY_ID = FI.FACILITY_ID "
			+ "WHERE P.petID = ?";
	private static final String SQL_SHOW_PET_SURVEY = "SELECT * FROM PetSurvey WHERE petID = ?";
	private static final String SQL_SHOW_PETS_BY_FACILITY = "SELECT P.petID, P.FACILITY_ID, P.CATEGORY_ID, PI.gender, PI.age, PI.price, PI.imagePath "
			+ "FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID "
			+ "WHERE P.FACILITY_ID = ? ORDER BY P.petID";
	private static final String SQL_SHOW_FAVORITE_PETS = """
			SELECT
			    P.petID,
			    FI.FACILITY_ID,
			    FI.facilityName,
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
			""";
	private static final String SQL_GET_ALL_MATCH_RATE = "SELECT P.petID, COUNT(US.QuestionID) * 10 AS matchRate "
			+ "FROM Pet P "
			+ "JOIN PetSurvey PS "
			+ "ON P.petID = PS.petID "
			+ "AND PS.QuestionID BETWEEN 1 AND 10 "
			+ "LEFT JOIN UserSurvey US "
			+ "ON US.QuestionID = PS.QuestionID "
			+ "AND US.SurveyChoiceID = PS.SurveyChoiceID "
			+ "AND US.USER_ID = ? "
			+ "AND US.QuestionID BETWEEN 1 AND 10 "
			+ "GROUP BY P.petID";
	private static final String SQL_COUNT_PETS_BY_FACILITY = "SELECT COUNT(*) AS CNT FROM Pet WHERE FACILITY_ID = ?";
	private static final String SQL_FIND_LATEST_PET = """
			SELECT TOP 1
			    P.petID,
			    FI.FACILITY_ID,
			    FI.facilityName,
			    PI.name,
			    PI.gender,
			    PI.age,
			    PI.price,
			    PI.imagePath
			FROM Pet P
			JOIN FacilityInformation FI
			    ON P.FACILITY_ID = FI.FACILITY_ID
			JOIN PetInformation PI
			    ON P.petID = PI.petID
			WHERE P.FACILITY_ID = ?
			ORDER BY P.petID DESC
			""";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError("JDBCドライバを読み込めませんでした");
		}
	}

	public int createPet(Pet pet) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PET)) {

			statement.setString(1, pet.getFacilityID());
			statement.setInt(2, pet.getCategoryID());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean createPetInformation(PetInformation petInformation) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PET_INFORMATION)) {

			bindPetInformation(statement, petInformation);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createPetSurvey(PetSurvey petSurvey) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PET_SURVEY)) {

			bindPetSurvey(statement, petSurvey);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePet(Pet pet) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PET)) {

			statement.setString(1, pet.getFacilityID());
			statement.setInt(2, pet.getCategoryID());
			statement.setInt(3, pet.getPetID());

			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetInformation(PetInformation petInformation) {
		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PET_INFORMATION)) {

			bindPetInformationUpdate(statement, petInformation);
			return statement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetSurvey(int petId, int questionId, int surveyChoiceId) {
		try (Connection connection = DButil.getConnection()) {
			if (updateExistingPetSurvey(connection, petId, questionId, surveyChoiceId)) {
				return true;
			}
			return insertPetSurvey(connection, petId, questionId, surveyChoiceId);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePet(int petId) {
		try (Connection connection = DButil.getConnection()) {
			clearFavoritePet(connection, petId);
			return deletePetById(connection, petId);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<PetInformationView> showList() {
		List<PetInformationView> petList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_PETS);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				petList.add(toPetInformationView(resultSet));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return petList;
	}

	public PetDetail showPetDetail(int petId) {
		PetDetail petDetail = null;

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_PET_DETAIL)) {

			statement.setInt(1, petId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					petDetail = toPetDetail(resultSet, petId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return petDetail;
	}

	public List<PetSurvey> showPetSurvey(int petId) {
		List<PetSurvey> petSurveyList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_PET_SURVEY)) {

			statement.setInt(1, petId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					petSurveyList.add(toPetSurvey(resultSet, petId));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return petSurveyList;
	}

	public List<PetInformationView> showListByFacility(String facilityId) {
		List<PetInformationView> facilityList = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_PETS_BY_FACILITY)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					facilityList.add(toPetInformationViewWithImage(resultSet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return facilityList;
	}

	public List<FavoritePet> showFavoritePet() {
		List<FavoritePet> favoritePets = new ArrayList<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SHOW_FAVORITE_PETS);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				favoritePets.add(toFavoritePet(resultSet));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return favoritePets;
	}

	public Map<Integer, Integer> getAllMatchRate(String userId) {
		Map<Integer, Integer> allMatchRateMap = new HashMap<>();

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MATCH_RATE)) {

			statement.setString(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					allMatchRateMap.put(resultSet.getInt("petID"), resultSet.getInt("matchRate"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allMatchRateMap;
	}

	public List<PetDetail> searchAllPet(
			String categoryId,
			String gender,
			String[] colorArray,
			String petSize,
			String ageRange,
			String priceRange) {

		List<PetDetail> searchPetList = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
				.append("P.petID, ")
				.append("P.CATEGORY_ID, ")
				.append("P.FACILITY_ID, ")
				.append("C.CATEGORY_NAME, ")
				.append("PI.petInformationID, ")
				.append("PI.name, ")
				.append("PI.gender, ")
				.append("PI.age, ")
				.append("PI.color, ")
				.append("PI.pet_size, ")
				.append("PI.vaccine, ")
				.append("PI.price, ")
				.append("PI.commentText, ")
				.append("PI.imagePath, ")
				.append("FI.facilityName, ")
				.append("FI.address, ")
				.append("FI.tel ")
				.append("FROM Pet P ")
				.append("JOIN PetInformation PI ON P.petID = PI.petID ")
				.append("JOIN Category C ON P.CATEGORY_ID = C.CATEGORY_ID ")
				.append("JOIN FacilityInformation FI ON P.FACILITY_ID = FI.FACILITY_ID ")
				.append("WHERE 1 = 1 ");

		List<Object> selectedList = new ArrayList<>();

		appendCategoryCondition(sql, selectedList, categoryId);
		appendGenderCondition(sql, selectedList, gender);
		appendPetSizeCondition(sql, selectedList, petSize);
		appendAgeCondition(sql, ageRange);
		appendPriceCondition(sql, priceRange);

		sql.append("ORDER BY P.petID");

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {

			bindSearchParameters(statement, selectedList);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					PetDetail pet = toPetDetail(resultSet, resultSet.getInt("petID"));
					if (!matchesColors(pet.getColor(), colorArray)) {
						continue;
					}
					searchPetList.add(pet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return searchPetList;
	}

	public int countByfacilityID(String facilityId) {
		int petCount = 0;

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_COUNT_PETS_BY_FACILITY)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					petCount = resultSet.getInt("CNT");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return petCount;
	}

	public FavoritePet findLatestPetByFacilityID(String facilityId) {
		FavoritePet latestPet = null;

		try (Connection connection = DButil.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_LATEST_PET)) {

			statement.setString(1, facilityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					latestPet = toFavoritePet(resultSet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return latestPet;
	}

	private boolean updateExistingPetSurvey(Connection connection, int petId, int questionId, int surveyChoiceId)
			throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PET_SURVEY)) {
			statement.setInt(1, surveyChoiceId);
			statement.setInt(2, petId);
			statement.setInt(3, questionId);
			return statement.executeUpdate() == 1;
		}
	}

	private boolean insertPetSurvey(Connection connection, int petId, int questionId, int surveyChoiceId)
			throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PET_SURVEY)) {
			statement.setInt(1, petId);
			statement.setInt(2, questionId);
			statement.setInt(3, surveyChoiceId);
			return statement.executeUpdate() == 1;
		}
	}

	private void bindPetInformation(PreparedStatement statement, PetInformation petInformation) throws Exception {
		statement.setInt(1, petInformation.getPetID());
		statement.setString(2, petInformation.getName());
		statement.setString(3, petInformation.getGender());
		statement.setInt(4, petInformation.getAge());
		statement.setString(5, petInformation.getColor());
		statement.setString(6, petInformation.getPet_size());
		statement.setString(7, petInformation.getVaccine());
		statement.setInt(8, petInformation.getPrice());
		statement.setString(9, petInformation.getCommentText());
		statement.setString(10, petInformation.getImagePath());
	}

	private void bindPetInformationUpdate(PreparedStatement statement, PetInformation petInformation) throws Exception {
		statement.setString(1, petInformation.getName());
		statement.setString(2, petInformation.getGender());
		statement.setInt(3, petInformation.getAge());
		statement.setString(4, petInformation.getColor());
		statement.setString(5, petInformation.getPet_size());
		statement.setString(6, petInformation.getVaccine());
		statement.setInt(7, petInformation.getPrice());
		statement.setString(8, petInformation.getCommentText());
		statement.setString(9, petInformation.getImagePath());
		statement.setInt(10, petInformation.getPetID());
	}

	private void bindPetSurvey(PreparedStatement statement, PetSurvey petSurvey) throws Exception {
		statement.setInt(1, petSurvey.getPetID());
		statement.setInt(2, petSurvey.getQuestionID());
		statement.setInt(3, petSurvey.getSurveyChoiceID());
	}

	private void clearFavoritePet(Connection connection, int petId) throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_CLEAR_FAVORITE_PET)) {
			statement.setInt(1, petId);
			statement.executeUpdate();
		}
	}

	private boolean deletePetById(Connection connection, int petId) throws Exception {
		try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PET)) {
			statement.setInt(1, petId);
			return statement.executeUpdate() == 1;
		}
	}

	private PetInformationView toPetInformationView(ResultSet resultSet) throws Exception {
		return new PetInformationView(
				resultSet.getInt("petID"),
				resultSet.getString("FACILITY_ID"),
				resultSet.getInt("CATEGORY_ID"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getInt("price"));
	}

	private PetInformationView toPetInformationViewWithImage(ResultSet resultSet) throws Exception {
		return new PetInformationView(
				resultSet.getInt("petID"),
				resultSet.getString("FACILITY_ID"),
				resultSet.getInt("CATEGORY_ID"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getInt("price"),
				resultSet.getString("imagePath"));
	}

	private PetDetail toPetDetail(ResultSet resultSet, int petId) throws Exception {
		return new PetDetail(
				petId,
				resultSet.getInt("CATEGORY_ID"),
				resultSet.getString("FACILITY_ID"),
				resultSet.getString("CATEGORY_NAME"),
				resultSet.getInt("petInformationID"),
				resultSet.getString("name"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getString("color"),
				resultSet.getString("pet_size"),
				resultSet.getString("vaccine"),
				resultSet.getInt("price"),
				resultSet.getString("commentText"),
				resultSet.getString("imagePath"),
				resultSet.getString("facilityName"),
				resultSet.getString("address"),
				resultSet.getString("tel"));
	}

	private PetSurvey toPetSurvey(ResultSet resultSet, int petId) throws Exception {
		return new PetSurvey(
				petId,
				resultSet.getInt("questionID"),
				resultSet.getInt("surveyChoiceID"));
	}

	private FavoritePet toFavoritePet(ResultSet resultSet) throws Exception {
		return new FavoritePet(
				resultSet.getInt("petID"),
				resultSet.getString("FACILITY_ID"),
				resultSet.getString("facilityName"),
				resultSet.getString("name"),
				resultSet.getString("gender"),
				resultSet.getInt("age"),
				resultSet.getInt("price"),
				resultSet.getString("imagePath"));
	}

	private void appendCategoryCondition(StringBuilder sql, List<Object> selectedList, String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			sql.append("AND P.CATEGORY_ID = ? ");
			selectedList.add(Integer.parseInt(categoryId));
		}
	}

	private void appendGenderCondition(StringBuilder sql, List<Object> selectedList, String gender) {
		if (gender != null && !gender.isEmpty()) {
			sql.append("AND PI.gender = ? ");
			selectedList.add(gender);
		}
	}

	private void appendPetSizeCondition(StringBuilder sql, List<Object> selectedList, String petSize) {
		if (petSize != null && !petSize.isEmpty()) {
			sql.append("AND PI.pet_size = ? ");
			selectedList.add(petSize);
		}
	}

	private void appendAgeCondition(StringBuilder sql, String ageRange) {
		if (ageRange == null || ageRange.isEmpty()) {
			return;
		}

		if ("age0".equals(ageRange)) {
			sql.append("AND PI.age = 0 ");
		} else if ("age1to3".equals(ageRange)) {
			sql.append("AND PI.age BETWEEN 1 AND 3 ");
		} else if ("age4up".equals(ageRange)) {
			sql.append("AND PI.age >= 4 ");
		}
	}

	private void appendPriceCondition(StringBuilder sql, String priceRange) {
		if (priceRange == null || priceRange.isEmpty()) {
			return;
		}

		if ("price0to100000".equals(priceRange)) {
			sql.append("AND PI.price BETWEEN 0 AND 100000 ");
		} else if ("price100001to300000".equals(priceRange)) {
			sql.append("AND PI.price BETWEEN 100001 AND 300000 ");
		} else if ("price300001to500000".equals(priceRange)) {
			sql.append("AND PI.price BETWEEN 300001 AND 500000 ");
		} else if ("price500001up".equals(priceRange)) {
			sql.append("AND PI.price >= 500001 ");
		}
	}

	private void bindSearchParameters(PreparedStatement statement, List<Object> selectedList) throws Exception {
		for (int i = 0; i < selectedList.size(); i++) {
			Object value = selectedList.get(i);
			if (value instanceof Integer) {
				statement.setInt(i + 1, (Integer) value);
			} else {
				statement.setString(i + 1, (String) value);
			}
		}
	}

	private boolean matchesColors(String dbColor, String[] selectedColors) {
		if (selectedColors == null || selectedColors.length == 0) {
			return true;
		}
		if (dbColor == null || dbColor.isEmpty()) {
			return false;
		}

		List<String> dbColorList = new ArrayList<>();
		for (String color : dbColor.split(",")) {
			dbColorList.add(color.trim());
		}

		for (String selectedColor : selectedColors) {
			if (!dbColorList.contains(selectedColor)) {
				return false;
			}
		}
		return true;
	}
}
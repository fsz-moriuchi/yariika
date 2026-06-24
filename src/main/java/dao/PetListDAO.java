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

	public int createPet(Pet pet) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_PET)) {

			stmt.setString(1, pet.getFacilityID());
			stmt.setInt(2, pet.getCategoryID());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean createPetInformation(PetInformation petInformation) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_PET_INFORMATION)) {

			bindPetInformation(stmt, petInformation);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createPetSurvey(PetSurvey petSurvey) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_PET_SURVEY)) {

			bindPetSurvey(stmt, petSurvey);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePet(Pet pet) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_PET)) {

			stmt.setString(1, pet.getFacilityID());
			stmt.setInt(2, pet.getCategoryID());
			stmt.setInt(3, pet.getPetID());

			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetInformation(PetInformation petInformation) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_PET_INFORMATION)) {

			bindPetInformationUpdate(stmt, petInformation);
			return stmt.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetSurvey(int petID, int questionID, int surveyChoiceID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			if (updateExistingPetSurvey(conn, petID, questionID, surveyChoiceID)) {
				return true;
			}
			return insertPetSurvey(conn, petID, questionID, surveyChoiceID);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePet(int petID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection()) {
			clearFavoritePet(conn, petID);
			return deletePetById(conn, petID);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<PetInformationView> showList() {
		List<PetInformationView> petList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_PETS);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				petList.add(toPetInformationView(rs));
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
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_PET_DETAIL)) {

			stmt.setInt(1, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					petDetail = toPetDetail(rs, petID);
				}
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
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_PET_SURVEY)) {

			stmt.setInt(1, petID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					petSurveyList.add(toPetSurvey(rs, petID));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return petSurveyList;
	}

	public List<PetInformationView> showListByFacility(String facilityId) {
		List<PetInformationView> facilityList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_PETS_BY_FACILITY)) {

			stmt.setString(1, facilityId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					facilityList.add(toPetInformationViewWithImage(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return facilityList;
	}

	public List<FavoritePet> showFavoritePet() {
		List<FavoritePet> list = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SHOW_FAVORITE_PETS);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				list.add(toFavoritePet(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Map<Integer, Integer> getAllMatchRate(String userId) {
		Map<Integer, Integer> allMatchRateMap = new HashMap<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL_MATCH_RATE)) {

			stmt.setString(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					allMatchRateMap.put(rs.getInt("petID"), rs.getInt("matchRate"));
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
			String pet_size,
			String ageRange,
			String priceRange) {

		List<PetDetail> searchPetList = new ArrayList<>();

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

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
		appendPetSizeCondition(sql, selectedList, pet_size);
		appendAgeCondition(sql, ageRange);
		appendPriceCondition(sql, priceRange);

		sql.append("ORDER BY P.petID");

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			bindSearchParameters(stmt, selectedList);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					PetDetail pet = toPetDetail(rs, rs.getInt("petID"));
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

	public int countByfacilityID(String facilityID) {
		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		int petCount = 0;

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_COUNT_PETS_BY_FACILITY)) {

			stmt.setString(1, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					petCount = rs.getInt("CNT");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return petCount;
	}

	public FavoritePet findLatestPetByFacilityID(String facilityID) {
		FavoritePet latestPet = null;

		try {
			loadJdbcDriver();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DButil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_FIND_LATEST_PET)) {

			stmt.setString(1, facilityID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					latestPet = toFavoritePet(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return latestPet;
	}

	private boolean updateExistingPetSurvey(Connection conn, int petID, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_PET_SURVEY)) {
			stmt.setInt(1, surveyChoiceID);
			stmt.setInt(2, petID);
			stmt.setInt(3, questionID);
			return stmt.executeUpdate() == 1;
		}
	}

	private boolean insertPetSurvey(Connection conn, int petID, int questionID, int surveyChoiceID)
			throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_PET_SURVEY)) {
			stmt.setInt(1, petID);
			stmt.setInt(2, questionID);
			stmt.setInt(3, surveyChoiceID);
			return stmt.executeUpdate() == 1;
		}
	}

	private void loadJdbcDriver() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}

	private void bindPetInformation(PreparedStatement stmt, PetInformation petInformation) throws Exception {
		stmt.setInt(1, petInformation.getPetID());
		stmt.setString(2, petInformation.getName());
		stmt.setString(3, petInformation.getGender());
		stmt.setInt(4, petInformation.getAge());
		stmt.setString(5, petInformation.getColor());
		stmt.setString(6, petInformation.getPet_size());
		stmt.setString(7, petInformation.getVaccine());
		stmt.setInt(8, petInformation.getPrice());
		stmt.setString(9, petInformation.getCommentText());
		stmt.setString(10, petInformation.getImagePath());
	}

	private void bindPetInformationUpdate(PreparedStatement stmt, PetInformation petInformation) throws Exception {
		stmt.setString(1, petInformation.getName());
		stmt.setString(2, petInformation.getGender());
		stmt.setInt(3, petInformation.getAge());
		stmt.setString(4, petInformation.getColor());
		stmt.setString(5, petInformation.getPet_size());
		stmt.setString(6, petInformation.getVaccine());
		stmt.setInt(7, petInformation.getPrice());
		stmt.setString(8, petInformation.getCommentText());
		stmt.setString(9, petInformation.getImagePath());
		stmt.setInt(10, petInformation.getPetID());
	}

	private void bindPetSurvey(PreparedStatement stmt, PetSurvey petSurvey) throws Exception {
		stmt.setInt(1, petSurvey.getPetID());
		stmt.setInt(2, petSurvey.getQuestionID());
		stmt.setInt(3, petSurvey.getSurveyChoiceID());
	}

	private void clearFavoritePet(Connection conn, int petID) throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_CLEAR_FAVORITE_PET)) {
			stmt.setInt(1, petID);
			stmt.executeUpdate();
		}
	}

	private boolean deletePetById(Connection conn, int petID) throws Exception {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_PET)) {
			stmt.setInt(1, petID);
			return stmt.executeUpdate() == 1;
		}
	}

	private PetInformationView toPetInformationView(ResultSet rs) throws Exception {
		return new PetInformationView(
				rs.getInt("petID"),
				rs.getString("FACILITY_ID"),
				rs.getInt("CATEGORY_ID"),
				rs.getString("gender"),
				rs.getInt("age"),
				rs.getInt("price"));
	}

	private PetInformationView toPetInformationViewWithImage(ResultSet rs) throws Exception {
		return new PetInformationView(
				rs.getInt("petID"),
				rs.getString("FACILITY_ID"),
				rs.getInt("CATEGORY_ID"),
				rs.getString("gender"),
				rs.getInt("age"),
				rs.getInt("price"),
				rs.getString("imagePath"));
	}

	private PetDetail toPetDetail(ResultSet rs, int petID) throws Exception {
		return new PetDetail(
				petID,
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
	}

	private PetSurvey toPetSurvey(ResultSet rs, int petID) throws Exception {
		return new PetSurvey(
				petID,
				rs.getInt("questionID"),
				rs.getInt("surveyChoiceID"));
	}

	private FavoritePet toFavoritePet(ResultSet rs) throws Exception {
		return new FavoritePet(
				rs.getInt("petID"),
				rs.getString("FACILITY_ID"),
				rs.getString("facilityName"),
				rs.getString("name"),
				rs.getString("gender"),
				rs.getInt("age"),
				rs.getInt("price"),
				rs.getString("imagePath"));
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

	private void bindSearchParameters(PreparedStatement stmt, List<Object> selectedList) throws Exception {
		for (int i = 0; i < selectedList.size(); i++) {
			Object value = selectedList.get(i);
			if (value instanceof Integer) {
				stmt.setInt(i + 1, (Integer) value);
			} else {
				stmt.setString(i + 1, (String) value);
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
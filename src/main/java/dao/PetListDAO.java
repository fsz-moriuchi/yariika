package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pet;
import model.PetDetail;
import model.PetInformation;
import model.PetInformationView;

public class PetListDAO {
	private final String JDBC_URL = "jdbc:sqlserver://localhost\\\\SQLEXPRESS:58956;databaseName=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

	public boolean createPet(Pet pet) {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {

			String sql = "INSERT INTO Pet(petID,category) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, pet.getPetID());
			pStmt.setString(2, pet.getCategory());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createPetInformation(PetInformation petInformation) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {

			String sql = "INSERT INTO PetInformation( petInformationID, petID, name, gender, age, color, pet_size, vaccine, price, commentText) VALUES(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, petInformation.getPetInformationID());
			pStmt.setInt(2, petInformation.getPetID());
			pStmt.setString(3, petInformation.getName());
			pStmt.setString(4, petInformation.getGender());
			pStmt.setInt(5, petInformation.getAge());
			pStmt.setString(6, petInformation.getColor());
			pStmt.setString(7, petInformation.getPet_size());
			pStmt.setString(8, petInformation.getVaccine());
			pStmt.setInt(9, petInformation.getPrice());
			pStmt.setString(10, petInformation.getCommentText());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<PetInformationView> showList() {
		List<PetInformationView> petList = new ArrayList<>();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"JDBCドライバは読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {

			String sql = "SELECT P.petID, P.category, PI.gender, PI.age, PI.price FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID ORDER BY P.petID";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int petID = rs.getInt("petID");
				String category = rs.getString("category");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				int price = rs.getInt("price");
				PetInformationView petInformationView = new PetInformationView(petID, category, gender, age, price);
				petList.add(petInformationView);
			}
		} catch (SQLException e) {
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

		try (Connection conn = DriverManager.getConnection(JDBC_URL)) {

			String sql = "SELECT P.petID, P.category, PI.petInformationID, PI.name, PI.gender, PI.age, PI.color, PI.pet_size, PI.vaccine, PI.price, PI.commentText FROM Pet P JOIN PetInformation PI ON P.petID = PI.petID WHERE P.petID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, petID);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String category = rs.getString("category");
				int petInformationID = rs.getInt("petInformationID");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String color = rs.getString("color");
				String pet_size = rs.getString("pet_size");
				String vaccine = rs.getString("vaccine");
				int price = rs.getInt("price");
				String commentText = rs.getString("commentText");
				petDetail = new PetDetail(petID, category, petInformationID, name, gender, age, color, pet_size,
						vaccine, price, commentText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return petDetail;

	}

}

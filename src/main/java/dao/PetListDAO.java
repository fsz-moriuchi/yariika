package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Pet;
import model.PetInformation;

public class PetListDAO {
	
	private final String JDBC_URL =
			"jdbc:sqlserver://localhost\\\\\\\\SQLEXPRESS:53375;databaseName=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
	public boolean createPet(Pet pet){
	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL)){
			
			String sql = "INSERT INTO Pet(petID,category) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			pStmt.setInt(1, pet.getPetID());
			pStmt.setString(2, pet.getCategory());
			
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
		public boolean createPetInformation(PetInformation petInformation){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL)){
			
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
			if(result != 1) {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

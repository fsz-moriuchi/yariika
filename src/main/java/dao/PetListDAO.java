package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Pet;

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

}

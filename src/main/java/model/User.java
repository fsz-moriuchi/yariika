package model;

public class User {
	private String userId;
	private String passwordHash;
	
	public User(String userId, String passwordHash) {
		this.userId = userId;
		this.passwordHash = passwordHash;
	}
	
	public String getUserId() {
		return userId;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
}

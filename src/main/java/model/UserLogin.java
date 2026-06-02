package model;

public class UserLogin {
	private String userId;
	private String passwordHash;

	public UserLogin(String userId, String passwordHash) {
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

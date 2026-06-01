package model;

public class Login {
	private String userId;
	private String passwordHash;

	public Login(String userId, String passwordHash) {
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

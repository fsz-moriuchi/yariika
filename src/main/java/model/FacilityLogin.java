package model;

public class FacilityLogin {
	private String facilityId;
	private String passwordHash;

	public FacilityLogin(String facilityId, String passwordHash) {
		this.facilityId = facilityId;
		this.passwordHash = passwordHash;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

}

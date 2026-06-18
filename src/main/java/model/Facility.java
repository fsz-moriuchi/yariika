package model;

public class Facility {
	private String facilityId;
	private String passwordHash;

	public Facility(String facilityId, String passwordHash) {
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

package model;

public class FacilityHomeView {

	private String facilityID;
	private String facilityName;
	private String address;
	private String tel;
	private String mail;
	private String openTime;
	private String closeTime;

	public FacilityHomeView(String facilityID, String facilityName, String address, String tel, String mail,
			String openTime, String closeTime) {
		this.facilityID = facilityID;
		this.facilityName = facilityName;
		this.address = address;
		this.tel = tel;
		this.mail = mail;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getMail() {
		return mail;
	}

	public String getOpenTimeDisplay() {
		return openTime.substring(0, 5);
	}

	public String getCloseTimeDisplay() {
		return closeTime.substring(0, 5);
	}
}
package model;

public class FacilityInformation {

	private String facilityId;
	private String facilityName;
	private String tel;
	private String address;
	private String mail;
	private String openTime;
	private String closeTime;
	private String closedDay;

	public FacilityInformation(String facilityId, String facilityName,
			String tel, String address, String mail,
			String openTime, String closeTime, String closedDay) {

		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.tel = tel;
		this.address = address;
		this.mail = mail;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.closedDay = closedDay;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getClosedDay() {
		return closedDay;
	}

	public void setClosedDay(String closedDay) {
		this.closedDay = closedDay;
	}
}
package model;

import java.time.LocalTime;

public class FacilityInformation {
	private int facilityInformationID;
	private String facilityID;
	private String facilityName;
	private String tel;
	private String address;
	private String mail;
	private LocalTime openTime;
	private LocalTime closeTime;
	private String closedDay;

	public FacilityInformation(int facilityInformationID, String facilityID, String facilityName, String tel,
			String address, String mail, LocalTime openTime, LocalTime closeTime, String closedDay) {
		this.facilityInformationID = facilityInformationID;
		this.facilityID = facilityID;
		this.facilityName = facilityName;
		this.tel = tel;
		this.address = address;
		this.mail = mail;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.closedDay = closedDay;
	}

	public int getFacilityInformationID() {
		return facilityInformationID;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getTel() {
		return tel;
	}

	public String getAddress() {
		return address;
	}

	public String getMail() {
		return mail;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}

	public LocalTime getCloseTime() {
		return closeTime;
	}

	public String getClosedDay() {
		return closedDay;
	}

}

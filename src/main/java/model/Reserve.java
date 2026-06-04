package model;

import java.time.LocalDateTime;

public class Reserve {
	private int reservationID;
	private int petID;
	private String userID;
	private String facilityID;
	private String reserveStatus;
	private LocalDateTime reserveTime;

	public Reserve(int reservationID, int petID, String userID, String facilityID, String reserveStatus,
			LocalDateTime reserveTime) {
		this.reservationID = reservationID;
		this.petID = petID;
		this.userID = userID;
		this.facilityID = facilityID;
		this.reserveStatus = reserveStatus;
		this.reserveTime = reserveTime;
	}

	public int getReservationID() {
		return reservationID;
	}

	public int getPetID() {
		return petID;
	}

	public String getUserID() {
		return userID;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public String getReserveStatus() {
		return reserveStatus;
	}

	public LocalDateTime getReserveTime() {
		return reserveTime;
	}
}

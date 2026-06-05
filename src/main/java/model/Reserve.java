package model;

import java.time.LocalDateTime;

public class Reserve {
	private int reservationID;
	private int petID;
	private String userID;
	private LocalDateTime reserveTime;

	public Reserve(int reservationID, int petID, String userID, LocalDateTime reserveTime) {
		this.reservationID = reservationID;
		this.petID = petID;
		this.userID = userID;
		this.reserveTime = reserveTime;
	}
	
	public Reserve(int petID, String userID, LocalDateTime reserveTime) {
		this.petID = petID;
		this.userID = userID;
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

	public LocalDateTime getReserveTime() {
		return reserveTime;
	}
}

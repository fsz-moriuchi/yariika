package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
	 public String getFormattedReserveTime() {

	        if (reserveTime == null) {
	            return "";
	        }

	        DateTimeFormatter formatter =
	                DateTimeFormatter.ofPattern(
	                        "yyyy年M月d日（E）HH:mm",
	                        Locale.JAPANESE);

	        return reserveTime.format(formatter);
	    }
}

package model;

import java.time.LocalDateTime;

public class ReserveView {
	private int reservationID; 
	private int petID; 
	private String petName; 
	private String imagePath; 
	private String userID; 
	private String userName; 
	private int userAge; 
	private String userTel; 
	private String userMail; 
	private LocalDateTime reserveTime; 
	private String formattedReserveTime;	

public ReserveView(int reservationID, int petID, String petName, String imagePath, String userID, String userName, int userAge, String userTel, String userMail, LocalDateTime reserveTime, String formattedReserveTime) {
	this.reservationID = reservationID;
	this.petID = petID;
	this.petName = petName;
	this.imagePath = imagePath;
	this.userID = userID;
	this.userName = userName;
	this.userAge = userAge;
	this.userTel = userTel;
	this.userMail = userMail;
	this.reserveTime = reserveTime;
	this.formattedReserveTime = formattedReserveTime;
}

public int getReservationID() {
	return reservationID;
}

public int getPetID() {
	return petID;
}

public String getPetName() {
	return petName;
}

public String getImagePath() {
	return imagePath;
}

public String getUserID() {
	return userID;
}

public String getUserName() {
	return userName;
}

public int getUserAge() {
	return userAge;
}

public String getUserTel() {
	return userTel;
}

public String getUserMail() {
	return userMail;
}

public LocalDateTime getReserveTime() {
	return reserveTime;
}

public String getFormattedReserveTime() {
	return formattedReserveTime;
}

}


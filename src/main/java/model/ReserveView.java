package model;

import java.time.LocalDateTime;

public class ReserveView {
	private int reservationID; 
	private int petID; 
	private String petName; 
	private String imagePath; 
	private String categoryName;
	private String petGender;
	private int petAge;
	private String userID; 
	private String userName; 
	private int userAge;
	private String userGender;
	private String userTel; 
	private String userMail; 
	private LocalDateTime reserveTime; 
	private String formattedReserveTime;	

public ReserveView(int reservationID, int petID, String petName, String imagePath, String categoryName, String petGender, int petAge, String userID, String userName, int userAge, String userGender, String userTel, String userMail, LocalDateTime reserveTime, String formattedReserveTime) {
	this.reservationID = reservationID;
	this.petID = petID;
	this.petName = petName;
	this.imagePath = imagePath;
	this.categoryName = categoryName;
	this.petGender = petGender;
	this.petAge = petAge;
	this.userID = userID;
	this.userName = userName;
	this.userAge = userAge;
	this.userGender = userGender;
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

public String getCategoryName() {
return categoryName;
}

public String getPetGender() {
return petGender;
}

public String getUserGender() {
return userGender;
}

public int getPetAge() {
	return petAge;
}

}


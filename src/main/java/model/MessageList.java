package model;

import java.sql.Timestamp;

public class MessageList {
	private String userId;
	private String userName;
	private int petID;
	private String petName;
	private Timestamp latestTime;
	
	//SELECT用
	public MessageList(String userId, String userName, int petID, String petName, Timestamp latestTime) {
		this.userId = userId;
		this.userName = userName;
		this.petID = petID;
		this.petName = petName;
		this.latestTime = latestTime;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public int getPetID() {
		return petID;
	}
	public String getPetName() {
		return petName;
	}
	public Timestamp getLatestTime() {
		return latestTime;
	}

}

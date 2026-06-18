package model;

import java.sql.Timestamp;

public class MessageList {
	private String userId;
	private String userName;
	private int petID;
	private String petName;
	private Timestamp latestTime;
	private String facilityId;
	private String facilityName;
	private  boolean isUser;
	private int unreadCount;
	
	//ユーザー側のメッセージ一覧SELECT用
	public MessageList(String facilityId, String facilityName, int petID, String petName, Timestamp latestTime, boolean isUser, int unreadCount) {
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.petID = petID;
		this.petName = petName;
		this.latestTime = latestTime;
		this.isUser = isUser;
		this.unreadCount = unreadCount;
	}
		
	
	
	//店舗側のメッセージ一覧SELECT用
	public MessageList(String userId, String userName, int petID, String petName, Timestamp latestTime, int unreadCount) {
		this.userId = userId;
		this.userName = userName;
		this.petID = petID;
		this.petName = petName;
		this.latestTime = latestTime;
		this.unreadCount = unreadCount;
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
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	
	public boolean getIsUser() {
		return isUser;
	}
	public int getUnreadCount() {
		return unreadCount;
	}
}

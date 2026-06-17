package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private int messageId;
	private String userId;
	private String facilityId;
	private int petID;
	private String messageText;
	private String senderType;
	private Timestamp createdAt;
	
	//SELECT用
	public Message(String userId, String facilityId, int petID, String messageText, String senderType, Timestamp createdAt) {
		this.userId = userId;
		this.facilityId = facilityId;
		this.petID = petID;
		this.messageText = messageText;
		this.senderType = senderType;
		this.createdAt = createdAt;
	}
	
	//INSERT用
	public Message(String userId, String facilityId, int petID, String messageText, String senderType) {
		this.userId = userId;
		this.facilityId = facilityId;
		this.petID = petID;
		this.messageText = messageText;
		this.senderType = senderType;
	}
	
	public int getMessageId() {
		return messageId;
	}
	public String getUserId() {
		return userId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public int getPetID() {
		return petID;
	}
	public String getMessageText() {
		return messageText;
	}
	public String getSenderType() {
		return senderType;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public String getFormattedTime() {
		LocalDateTime ldt = createdAt.toLocalDateTime(); 
	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
	    return ldt.format(fmt);
	}

}

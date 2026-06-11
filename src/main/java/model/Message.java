package model;

public class Message {
	private int messageId;
	private String userId;
	private String facilityId;
	private int petId;
	private String messageText;
	private String senderType;
	
	public Message(String userId, String facilityId, int petId, String messageText, String senderType) {
		//this.messageId = messageId;
		this.userId = userId;
		this.facilityId = facilityId;
		this.petId = petId;
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
	public int getPetId() {
		return petId;
	}
	public String getMessageText() {
		return messageText;
	}
	public String getSenderType() {
		return senderType;
	}

}

package model;

import java.sql.Date;

public class UserInfo {
	private int userInfoId;
	private String userId;
	private String userName;
	private String userGender;
	private Date userBirthday;
	private String userTel;
	private String userMail;
	private String userAddress;
	
	public UserInfo(int userInfoId, String userId, String userName, String userGender, Date userBirthday, String userTel, String userMail, String userAddress) {
		this.userInfoId = userInfoId;
		this.userId = userId;
		this.userName = userName;
		this.userGender = userGender;
		this.userBirthday = userBirthday;
		this.userTel = userTel;
		this.userMail = userMail;
		this.userAddress = userAddress;
	}
	
	public int getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

}

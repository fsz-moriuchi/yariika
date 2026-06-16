package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Reserve {
	private int reservationID;
	private int petID;
	private String userID;
	private LocalDateTime reserveTime;
	private String petName;
	private String gender;
	private int age;
	private String imagePath;
	private int categoryId;
	private String facilityName;
	private String address;
	private String tel;
	private String mail;
	private LocalTime openTime;
	private LocalTime closeTime;
	private String closedDay;
	private String userName;
	private String userGender;
	private Date userBirthday;
	private String userTel;
	private String userMail;

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
	
	//ユーザー予約確認情報詳しく
	public Reserve(int reservationID, int petID, String userID, LocalDateTime reserveTime,String petName,String gender,int age,String imagePath, int categoryId, String facilityName ,String address,String tel,String mail,LocalTime openTime,LocalTime closeTime,String closedDay, String userName, String userGender, Date userBirthday, String userTel, String userMail) {
		this.reservationID = reservationID;
		this.petID = petID;
		this.userID = userID;
		this.reserveTime = reserveTime;
		this.petName = petName;
		this.gender = gender;
		this.age = age;
		this.imagePath = imagePath;
		this.categoryId = categoryId;
		this.facilityName = facilityName;
		this.address =address;
		this.tel = tel;
		this.mail = mail;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.closedDay = closedDay;
		this.userName = userName;
		this.userGender = userGender;
		this.userBirthday = userBirthday;
		this.userTel = userTel;
		this.userMail = userMail;
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
	 public String getPetName() {
		 return petName;
	 }
	 
	 public String getGender() {
		if (gender == null) {
			 return "";
		}
		switch (gender) {
			case "male":
				return "男の子";
			case "female":
				return "女の子";
			default:
				return gender;
		}
	}
	 
	 public int getAge() {
		 return age;
	 }
	 public String getImagePath() {
		 return imagePath;
	 }
	 
	 public String getCategoryName() {
		if (categoryId < 1) {
				return "";
		}
		switch (categoryId) {
			case 1:
				return "犬";
			case 2:
				return "猫";
			case 3:
				return "鳥";
			case 4:
				return "小動物";
			default:
				return "";
		}
	 }
	 
	 public String getFacilityName() {
		 return facilityName;
	 }
	 public String getAddress() {
		 return address;
	 }
	 public String getTel() {
		 return tel;
	 }
	 public String getMail() {
		 return mail;
	 }
	 public LocalTime getOpenTime() {
		 return openTime;
	 }
	 public String getFormattedOpenTime() {

	        if (openTime == null) {
	            return "";
	        }

	        DateTimeFormatter formatter =
	                DateTimeFormatter.ofPattern(
	                        "yyyy年M月d日（E）HH:mm",
	                        Locale.JAPANESE);

	        return openTime.format(formatter);
	    }
	 public LocalTime getCloseTime() {
		 return closeTime;
	 }
	 public String getFormattedCloseTime() {

	        if (closeTime == null) {
	            return "";
	        }

	        DateTimeFormatter formatter =
	                DateTimeFormatter.ofPattern(
	                        "yyyy年M月d日（E）HH:mm",
	                        Locale.JAPANESE);

	        return closeTime.format(formatter);
	    }
	 
	 public String getClosedDay() {
		 if(closedDay == null || closedDay.isEmpty()) {
			 return "定休日なし";
		 }
		 String[] days = closedDay.split(",");
		 StringBuilder sb = new StringBuilder();
		 for (String day : days) {
			 day = day.trim();
			 switch (day) {
			 	case "MONDAY": sb.append("月曜日 "); break;
			 	case "TUESDAY": sb.append("火曜日 "); break;
			 	case "WEDNESDAY": sb.append("水曜日 "); break;
			 	case "THURSDAY": sb.append("木曜日 "); break;
			 	case "FRIDAY": sb.append("金曜日 "); break;
			 	case "SATURDAY": sb.append("土曜日 "); break;
			 	case "SUNDAY": sb.append("日曜日 "); break;
			 	default: sb.append(day + " ");
			 }
		 }
		 return sb.toString().trim();
	 }

	 
	 
	 public String getUserName() {
		 return userName;
	 }
	 public String getUserGender() {
		 return userGender;
	 }
	 
	 public String getUserGenderJa() {
		 if (userGender == null) return "";
		 switch (userGender) {
		   case "male": return "男性";
		   case "female": return "女性";
		   default: return userGender;
		 }
	}
	 public Date getUserBirthday() {
		 return userBirthday;
	 }
	 //年齢計算
	 public int getUserAge() {
		 if (userBirthday == null) return 0;
		 LocalDate birthday = userBirthday.toLocalDate();
		 return Period.between(birthday, LocalDate.now()).getYears();
	}
	 
	 public String getUserTel() {
		 return userTel;
	 }
	 public String getUserMail() {
		 return userMail;
	 }
	 
}

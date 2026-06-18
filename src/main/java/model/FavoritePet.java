package model;

public class FavoritePet {

	private String facilityName;
	private int petID;
	private String name;
	private String gender;
	private int age;
	private int price;
	private String imagePath;
	private int matchRate;
	private String facilityID;

	public FavoritePet(
			int petID,
			String facilityID,
			String facilityName,
			String name,
			String gender,
			int age,
			int price,
			String imagePath) {

		this.petID = petID;
		this.facilityID = facilityID;
		this.facilityName = facilityName;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.price = price;
		this.imagePath = imagePath;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public int getPetID() {
		return petID;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public int getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getMatchRate() {
		return matchRate;
	}

	public void setMatchRate(int matchRate) {
		this.matchRate = matchRate;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public String getGenderName() {
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
}
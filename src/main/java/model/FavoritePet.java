package model;

public class FavoritePet {

	private String facilityName;
	private int petID;
	private String name;
	private String gender;
	private int age;
	private int price;
	private String imagePath;

	public FavoritePet(
			String facilityName,
			int petID,
			String name,
			String gender,
			int age,
			int price,
			String imagePath) {

		this.facilityName = facilityName;
		this.petID = petID;
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
}
package model;

public class PetInformationView {
	private int petID;
	private String categoryName;
	private String gender;
	private int age;
	private int price;

	public PetInformationView(int petID, String categoryName, String gender, int age, int price) {
		this.petID = petID;
		this.categoryName = categoryName;
		this.gender = gender;
		this.age = age;
		this.price = price;
	}

	public int getPetID() {
		return petID;
	}

	public String getCategoryName() {
		return categoryName;
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
}

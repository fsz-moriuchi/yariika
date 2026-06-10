package model;

public class PetInformationView {
	private int petID;
	private int categoryID;
	private String gender;
	private int age;
	private int price;

	public PetInformationView(int petID, int categoryID, String gender, int age, int price) {
		this.petID = petID;
		this.categoryID = categoryID;
		this.gender = gender;
		this.age = age;
		this.price = price;
	}

	public int getPetID() {
		return petID;
	}

	public int getCategoryID() {
		return categoryID;
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

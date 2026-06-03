package model;

public class PetInformationView {
	private int petID;
	private String category;
	private String gender;
	private int age;
	private int price;

	public PetInformationView(int petID, String category, String gender, int age, int price) {
		this.petID = petID;
		this.category = category;
		this.gender = gender;
		this.age = age;
		this.price = price;
	}

	public int getPetID() {
		return petID;
	}

	public String getCategory() {
		return category;
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

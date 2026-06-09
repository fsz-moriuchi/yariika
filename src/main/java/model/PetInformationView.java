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
	
	public String getCategoryName() {
		if(category == null) {
			return "";
		}
		switch(category){
			case "dog" : return "犬";
			case "cat": return "猫";
			default : return category;
		}
	}
	public String getGenderName() {
		if(gender == null) {
			return "";
		}
		switch(gender){
			case "male" : return "男の子";
			case "female": return "女の子";
			default : return gender;
		}
	}

}

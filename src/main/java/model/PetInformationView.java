package model;

public class PetInformationView {
	private int petID;
	private String facilityId;
	private int categoryId;
	private String gender;
	private int age;
	private int price;

	public PetInformationView(int petID, String facilityId,int categoryId, String gender, int age, int price) {
		this.petID = petID;
		this.facilityId = facilityId;
		this.categoryId = categoryId;
		this.gender = gender;
		this.age = age;
		this.price = price;
	}

	public int getPetID() {
		return petID;
	}
	public String getFacilityId() {
		return facilityId;
	}

	public int getCategoryId() {
		return categoryId;
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
	
	public String getCategoryIdName() {
		if(categoryId < 1) {
			return "";
		}
		switch(categoryId){
			case 1 : return "犬";
			case 2: return "猫";
			case 3: return "鳥";
			case 4: return "小動物";
			default : return "";
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

package model;

public class PetDetail {
	private int petID;
	private String facilityID;
	private String categoryName;
	private int petInformationID;
	private String name;
	private String gender;
	private int age;
	private String color;
	private String pet_size;
	private String vaccine;
	private int price;
	private String commentText;

	public PetDetail(int petID, String facilityID , String categoryName, int petInformationID, String name, String gender, int age,
			String color, String pet_size, String vaccine, int price, String commentText) {
		this.petID = petID;
		this.facilityID = facilityID;
		this.categoryName = categoryName;
		this.petInformationID = petInformationID;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.color = color;
		this.pet_size = pet_size;
		this.vaccine = vaccine;
		this.price = price;
		this.commentText = commentText;
	}

	public int getPetID() {
		return petID;
	}
	
	public String getFacilityID() {
		return facilityID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public int getPetInformationID() {
		return petInformationID;
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

	public String getColor() {
		return color;
	}

	public String getPet_size() {
		return pet_size;
	}

	public String getVaccine() {
		return vaccine;
	}

	public int getPrice() {
		return price;
	}

	public String getCommentText() {
		return commentText;
	}

}

package model;

public class PetInformation {
	private int petInformationID;
	private int petID;
	private String name;
	private String gender;
	private int age;
	private String color;
	private int pet_size;
	private String vaccine;
	private int price;
	private String comment;
	
	public PetInformation() {};
	public PetInformation(int petInformationID,int petID,String name,String gender,int age,String color,int pet_size,String vaccine,int price,String comment) {
		this.petInformationID = petInformationID;
		this.petID = petID;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.color = color;
		this.pet_size = pet_size;
		this.vaccine = vaccine;
		this.price = price;
		this.comment = comment;
	}

	public int getPetInformationID() {
		return petInformationID;
	}

	public void setPetInformationID(int petInformationID) {
		this.petInformationID = petInformationID;
	}

	public int getPetID() {
		return petID;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPet_size() {
		return pet_size;
	}

	public void setPet_size(int pet_size) {
		this.pet_size = pet_size;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

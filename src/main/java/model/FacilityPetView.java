package model;

//所属ペット一覧用
public class FacilityPetView {

	private int petID;
	private String name;
	private String gender;
	private int age;
	private int price;
	private String imagePath;

	public FacilityPetView(
			int petID,
			String name,
			String gender,
			int age,
			int price,
			String imagePath) {

		this.petID = petID;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.price = price;
		this.imagePath = imagePath;
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

	public String getGenderName() {

		if ("male".equals(gender)) {
			return "男の子";
		}

		if ("female".equals(gender)) {
			return "女の子";
		}

		return "";
	}

}
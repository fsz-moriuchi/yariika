package model;

public class Pet {

	private int petID;
	private String category;

	public Pet(){};
	public Pet(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public int getPetID() {
		return petID;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}

}
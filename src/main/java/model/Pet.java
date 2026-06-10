package model;

public class Pet {

	private int petID;
	private String facilityID;
	private int categoryID;

	public Pet() {
	};

	public Pet(int petID, String facilityID , int categoryID) {
		this.petID = petID;
		this.categoryID = categoryID;
	}

	public int getCategoryID() {
		return categoryID;
	}
	
	public String getFacilityID() {
		return facilityID;
	}

	public int getPetID() {
		return petID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}

}
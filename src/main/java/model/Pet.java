package model;

public class Pet {

	private int petID;
	private String facilityID;
	private int categoryID;

	public Pet(String facilityID, int categoryID) {
		this.facilityID = facilityID;
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
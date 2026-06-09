package model;

public class Pet {

	private int petID;
	private String facilityId;
	private int categoryId;


	public Pet(){};
	public Pet(String facilityId,int categoryId) {
		this.facilityId = facilityId;
		this.categoryId = categoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public int getPetID() {
		return petID;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}
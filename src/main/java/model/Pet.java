package model;

public class Pet {

	private int petID;
	private String facilityId;
	private String category;


	public Pet(){};
	public Pet(String facilityId,String category) {
		this.facilityId = facilityId;
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
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}
package model;

//人気ランキング
public class PopularPetView {

	private int petID;
	private String name;
	private String imagePath;
	private int favoriteCount;

	public PopularPetView(
			int petID,
			String name,
			String imagePath,
			int favoriteCount) {

		this.petID = petID;
		this.name = name;
		this.imagePath = imagePath;
		this.favoriteCount = favoriteCount;
	}

	public int getPetID() {
		return petID;
	}

	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

}
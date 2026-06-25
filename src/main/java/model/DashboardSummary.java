package model;

public class DashboardSummary {
	private int todayReserveCount;
	private int petCount;
	private Reserve nextReserve;
	private FavoritePet latestPet;
	private int viewCount;
	private int unreadMessageCount;

	public int getTodayReserveCount() {
		return todayReserveCount;
	}

	public void setTodayReserveCount(int todayReserveCount) {
		this.todayReserveCount = todayReserveCount;
	}

	public int getPetCount() {
		return petCount;
	}

	public void setPetCount(int petCount) {
		this.petCount = petCount;
	}

	public Reserve getNextReserve() {
		return nextReserve;
	}

	public void setNextReserve(Reserve nextReserve) {
		this.nextReserve = nextReserve;
	}

	public FavoritePet getLatestPet() {
		return latestPet;
	}

	public void setLatestPet(FavoritePet latestPet) {
		this.latestPet = latestPet;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getUnreadMessageCount() {
		return unreadMessageCount;
	}

	public void setUnreadMessageCount(int unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}
}
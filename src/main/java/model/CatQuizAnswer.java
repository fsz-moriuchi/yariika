package model;

public class CatQuizAnswer {
	private int catQuizAnswerId;
	private String userId;
	private int catQuizId;
	private int catUserAnswer;
	
	public CatQuizAnswer(String userId,int catQuizId, int catUserAnswer) {
		this.userId = userId;
		this.catQuizId = catQuizId;
		this.catUserAnswer = catUserAnswer;
	}
	
	public int getCatQuizAnswerId() {
		return catQuizAnswerId;
	}
	public void setCatQuizAnswerId(int catQuizAnswerId) {
        this.catQuizAnswerId = catQuizAnswerId;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
        this.userId = userId;
    }
	public int getCatQuizId() {
		return catQuizId;
	}
	public void setCatQuizId(int catQuizId) {
		this.catQuizId = catQuizId;
	}
	public int getCatUserAnswer() {
		return catUserAnswer;
	}
	public void setCatUserAnswer(int catUserAnswer) {
		this.catUserAnswer = catUserAnswer;
	}
}

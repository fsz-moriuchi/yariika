package model;

public class DogQuizAnswer {
	private int dogQuizAnswerId;
	private String userId;
	private int dogQuizId;
	private int dogUserAnswer;
	
	public DogQuizAnswer(String userId, int dogQuizId, int dogUserAnswer) {
		this.userId = userId;
		this.dogQuizId = dogQuizId;
		this.dogUserAnswer = dogUserAnswer;
	}
	
	public int getDogQuizAnswerId() {
		return dogQuizAnswerId;
	}
	public void setDogQuizAnswerId(int dogQuizAnswerId) {
        this.dogQuizAnswerId = dogQuizAnswerId;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
        this.userId = userId;
    }
	public int getDogQuizId() {
		return dogQuizId;
	}
	public void setDogQuizId(int dogQuizId) {
		this.dogQuizId = dogQuizId;
	}
	public int getDogUserAnswer() {
		return dogUserAnswer;
	}
	public void setDogUserAnswer(int dogUserAnswer) {
		this.dogUserAnswer = dogUserAnswer;
	}

}

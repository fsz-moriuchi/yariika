package model;

public class QuizAnswer {
	private int quizAnswerId;
	private String userId;
	private int quizId;
	private int userAnswer;
	private String quizSessionId;
	
	public QuizAnswer(String userId,int quizId, int userAnswer, String quizSessionId) {
		this.userId = userId;
		this.quizId = quizId;
		this.userAnswer = userAnswer;
		this.quizSessionId = quizSessionId;
	}
	
	public int getQuizAnswerId() {
		return quizAnswerId;
	}
	public void setQuizAnswerId(int quizAnswerId) {
        this.quizAnswerId = quizAnswerId;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
        this.userId = userId;
    }
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public int getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(int userAnswer) {
		this.userAnswer = userAnswer;
	}
	public String getQuizSessionId() {
		return quizSessionId;
	}
	public void setQuizSessionId(String quizSessionId) {
		this.quizSessionId = quizSessionId;
	}
}

package model;

public class Question {

	private int QuestionID;
	private String userQuestion;
	private String petQuestion;
	
	public Question() {};
	public Question(int QuestionID,String userQuestion,String petQuestion) {
		this.QuestionID = QuestionID;
		this.userQuestion = userQuestion;
		this.petQuestion = petQuestion;
	}
	
	public int getQuestionID() {
		return QuestionID;
	}
	public String getUserQuestion() {
		return userQuestion;
	}
	public String getPetQuestion() {
		return petQuestion;
	}
}

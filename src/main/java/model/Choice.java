package model;

public class Choice {
	private int SurveyChoiceID;
	private int QuestionID;
	private String Choice;
	
	public Choice() {};
	public Choice(int SurveyChoiceID,int QuestionID,String Choice) {
		this.SurveyChoiceID = SurveyChoiceID;
		this.QuestionID = QuestionID;
		this.Choice = Choice;
	}
	
	public int getSurveyChoiceID() {
		return SurveyChoiceID;
	}
	public int getQuestionID() {
		return QuestionID;
	}
	public String getChoice() {
		return Choice;
	}
}

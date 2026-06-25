package model;

public class Choice {
	private int surveyChoiceId;
	private int questionId;
	private String choice;

	public Choice(int surveyChoiceId, int questionId, String choice) {
		this.surveyChoiceId = surveyChoiceId;
		this.questionId = questionId;
		this.choice = choice;
	}

	public int getSurveyChoiceId() {
		return surveyChoiceId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getChoice() {
		return choice;
	}
}
package model;

public class PetSurvey {

	private int petSurveyID;
	private int petID;
    private int questionID;
    private int surveyChoiceID;


    public PetSurvey(int petID, int questionID,int surveyChoiceID) {
        this.petID = petID;
    		this.surveyChoiceID = surveyChoiceID;
        this.questionID = questionID;
    }

    public int getPetSurveyID() {
    		return petSurveyID;
    }
    
    public void setPetSurveyID(int petSurveyID) {
    		this.petSurveyID = petSurveyID;
	}
    public int getPetID() {
    		return petID;
    	}
    public void setPetID(int petID) {
    		this.petID = petID;
    }
    	public int getQuestionID() {
    		return questionID;
    	}
    	public void setQuestionID(int questionID) {
    		this.questionID = questionID;
    		}
    	public int getSurveyChoiceID() {
    		return surveyChoiceID;
    		}
    	public void setSurveyChoiceID(int surveyChoiceID) {
    		this.surveyChoiceID = surveyChoiceID;
    	}
}


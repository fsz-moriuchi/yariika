package model;

public class UserSurvey {
	
	private int userSurveyID;
	private int userID;
    private int questionID;
    private int surveyChoiceID;


    public UserSurvey(int userID, int questionID,int surveyChoiceID) {
        this.userID = userID;
    		this.surveyChoiceID = surveyChoiceID;
        this.questionID = questionID;
    }

    public int getUserSurveyID() {
    		return userSurveyID;
    }
    
    public void setUserSurveyID(int userSurveyID) {
    		this.userSurveyID = userSurveyID;
	}
    public int getUserID() {
    		return userID;
    	}
    public void setUserID(int userID) {
    		this.userID = userID;
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

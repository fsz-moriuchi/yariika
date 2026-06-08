package model;

public class UserSurvey {
	
	private int userSurveyID;
	private String userId;
    private int questionID;
    private int surveyChoiceID;


    public UserSurvey(String userId, int questionID,int surveyChoiceID) {
        this.userId = userId;
        this.questionID = questionID;
    		this.surveyChoiceID = surveyChoiceID;

    }

    public int getUserSurveyID() {
    		return userSurveyID;
    }
    
    public void setUserSurveyID(int userSurveyID) {
    		this.userSurveyID = userSurveyID;
	}
    public String getUserId() {
    		return userId;
    	}
    public void setUserId(String userId) {
    		this.userId = userId;
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

package model;

public class DogQuizResult {
	private int dogQuizId;
	private String question;
	private int answer;
	private int dogUserAnswer;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private int dogQuizAnswerId;
	
	public DogQuizResult(int dogQuizId, String question, String choice1, String choice2, String choice3, String choice4, int answer, int dogUserAnswer) {
		this.question = question;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.answer = answer;
		this.dogUserAnswer = dogUserAnswer;
	}
	public int getDogQuizId() {
		return dogQuizId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getChoice1() {
		return choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getDogUserAnswer() {
		return dogUserAnswer;
	}
	public void setDogUserAnswer(int dogUserAnswer){
		this.dogUserAnswer = dogUserAnswer;
	}
	public int getDogQuizAnswerId() {
		return dogQuizAnswerId;
	}
	
	//正誤判定
	public boolean isCorrect() {
	    return answer == dogUserAnswer;
	}
	
	//選択肢の文字変換
	public String getUserAnswerText() {
		switch(dogUserAnswer) {
			case 1: return choice1;
			case 2: return choice2;
			case 3: return choice3;
			case 4: return choice4;
			default: return "";
		}
	}

	public String getCorrectAnswerText() {
		switch(answer) {
			case 1: return choice1;
			case 2: return choice2;
			case 3: return choice3;
			case 4: return choice4;
			default: return "";
		}
	}
}

package model;

public class QuizResult {
	private int quizId;
	private String question;
	private int answer;
	private int userAnswer;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private int quizAnswerId;
		
	public QuizResult(int quizId, String question, String choice1, String choice2, String choice3, String choice4, int answer, int userAnswer) {
		this.quizId = quizId;
		this.question = question;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.answer = answer;
		this.userAnswer = userAnswer;
	}
	public int getQuizId() {
		return quizId;
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
	public int getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(int userAnswer){
		this.userAnswer = userAnswer;
	}
	public int getQuizAnswerId() {
		return quizAnswerId;
	}
		
	//正誤判定
	public boolean isCorrect() {
		return answer == userAnswer;
	}
		
	//選択肢の文字変換
	public String getUserAnswerText() {
		switch(userAnswer) {
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
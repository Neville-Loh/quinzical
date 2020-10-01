package quinzical.model;

public class PracticeQuestion extends Question{
	
	private int attemptLeft;
	private boolean showHint;
	
	public PracticeQuestion(String question, String ans, String prefix) {
		super(question, ans, prefix);
		this.setisPractice(true);
		attemptLeft = 3;
		showHint = false;
	}
	
	public PracticeQuestion(Question question) {
		super(question.getPrompt(), question.getAnswer(), question.getAnswerPrefix());
		this.setID(question.getID());
		this.setisPractice(true);
		attemptLeft = 3;
		showHint = false;
	}

	public int getAttemptLeft() {
		return attemptLeft;
	}

	public void setAttemptLeft(int attemptLeft) {
		this.attemptLeft = attemptLeft;
		if (attemptLeft < 0){
			attemptLeft = 0;
		} else if (attemptLeft == 1) {
			showHint = true;
		}
	}

	public boolean isShowHint() {
		return showHint;
	}

	public void setShowHint(boolean showHint) {
		this.showHint = showHint;
	}

}

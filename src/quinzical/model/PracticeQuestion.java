package quinzical.model;

public class PracticeQuestion extends Question{
	
	private int attemptLeft;
	private boolean showHint;
	
	public PracticeQuestion(String question, String ans, String prefix) {
		super(question, ans, prefix);
	}

	public int getAttemptLeft() {
		return attemptLeft;
	}

	public void setAttemptLeft(int attemptLeft) {
		this.attemptLeft = attemptLeft;
	}

	public boolean isShowHint() {
		return showHint;
	}

	public void setShowHint(boolean showHint) {
		this.showHint = showHint;
	}

}

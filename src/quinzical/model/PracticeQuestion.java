package quinzical.model;

/**
 * Practice Question is a class which extends Question, has all the functionality related to 
 * questions but are straightly used for practice mode. 
 * @author Neville
 *
 */
public class PracticeQuestion extends Question{
	
	private int attemptLeft;
	private boolean showHint;
	
	/**
	 * Constructor, create a new practice question object
	 * @param question
	 * @param ans
	 * @param prefix
	 */
	public PracticeQuestion(String question, String ans, String prefix) {
		super(question, ans, prefix);
		this.setisPractice(true);
		attemptLeft = 3;
		showHint = false;
	}
	
	/**
	 * Constructor, parse a question object into practice question
	 * @param question
	 */
	public PracticeQuestion(Question question) {
		super(question.getPrompt(), question.getAnswer(), question.getAnswerPrefix());
		this.setID(question.getID());
		this.setisPractice(true);
		attemptLeft = 3;
		showHint = false;
	}

	/**
	 * Get the attempt left for the current practice question
	 * @return attempt left
	 */
	public int getAttemptLeft() {
		return attemptLeft;
	}
	
	/**
	 * Set the practice question attempt left, for practice mode only
	 * @param attemptLeft
	 */
	public void setAttemptLeft(int attemptLeft) {
		this.attemptLeft = attemptLeft;
		if (attemptLeft < 0){
			attemptLeft = 0;
		} else if (attemptLeft == 1) {
			showHint = true;
		}
	}

	/**
	 * Get a boolean represent if the practice question should show hint
	 * @return
	 */
	public boolean isShowHint() {
		return showHint;
	}
	
	/**
	 * set if the practice question should show hint
	 * @param showHint
	 */
	public void setShowHint(boolean showHint) {
		this.showHint = showHint;
	}

}

package quinzical.model;
/**
 * Class for implementation of the question data structure, the class stores the
 * question prompt, the answer and score of the question.
 * 
 * @author Neville
 */
public class Question {
	private int _id;
	private String _question;
	private String _answer;
	private int _score;
	private boolean _isAttempted = false;

	public Question(String question, String ans, int score) {
		_answer = ans;
		_question = question;
		_score = score;
	}
	

	/**
	 * set attribute isAttempted to a boolean
	 * @param bool
	 */
	public void setAttempted(boolean bool) {
		_isAttempted = bool;
	}

	/**
	 * return true if the question has already been attempted, else false
	 * @return isAttempted
	 */
	public boolean isAttempted() {
		return _isAttempted;
	}
	/**
	 * return the question prompt as a string.
	 */
	@Override
	public String toString() {
		return _question;
	}
	/**
	 * Get Method, get the answer of the question
	 * @return Answer
	 */
	public String getAnswer() {
		return _answer;
	}
	/**
	 * Get the score of the question
	 * @return question Score
	 */
	public int getScore() {
		return _score;
	}
	
	public int getID() {
		return _id;
	}
}

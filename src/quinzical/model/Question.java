package quinzical.model;
/**
 * Class for implementation of the question data structure, the class stores the
 * question prompt, the answer and score of the question.
 * 
 * @author Neville, danielcutfield
 */
public class Question {
	private int _id = -1;
	private String _question;
	private String _answer;
	private String _prefix;
	private int _score;
	private boolean _isAttempted = false;
	private boolean _isPractice = false;

	public Question(String question, String ans, int score) {
		_answer = ans;
		_question = question;
		_score = score;
	}

	/**
	 * Constructor of Object question, 
	 * @param question
	 * @param ans
	 * @param prefix
	 */
	public Question(String question, String ans, String prefix) {
		_answer = ans;
		_question = question;
		_prefix = prefix;
	}
	

	public Question(String question, String ans) {
		_answer = ans;
		_question = question;

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
	
	public void setScore(int score) {
		_score = score;
	}
	/**
	 * Get the score of the question
	 * @return question Score
	 */
	public int getScore() {
		return _score;
	}
	
	/**
	 * Get Method, get the question ID
	 * @return question id
	 */
	public int getID() {
		return _id;
	}
	
	/**
	 * Set method for ID,
	 * CAUTIOUS!, this method only meant to be called by database implementation and
	 * not Anywhere of the application, throw illegalArgumentException if id already exist.
	 * @param id
	 */
	public void setID(int id) {
		if (_id == -1) {
			_id = id;
		} else {
			throw new IllegalArgumentException("Id already exist for question: " + _question);
		}
		
	}
	
	/**
	 * Get Method, get the question prompt
	 * @return question prompt
	 */
	public String getPrompt() {
		return _question;
	}
	
	/**
	 * Get Method, get the prefix of the answer
	 * @return prefix
	 */
	public String getAnswerPrefix() {
		return _prefix;
	}
	
	/**
	 * Set Method, set the prefix the answer of this question
	 * @param answerPrefix
	 */
	public void setAnswerPrefix(String answerPrefix) {
		_prefix = answerPrefix;
	}

	public boolean isPractice() {
		return _isPractice;
	}

	public void setisPractice(boolean isPractice) {
		this._isPractice = isPractice;
	}
	

}

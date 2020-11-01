package quinzical.model;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store the current session related to the user 
 * @author Neville, danielcutfield
 *
 */

public class Session {
	

	private int _ID = -1;
	private User _user;
	private int _winnings;
	private List<Category> _cats;
	private Map<Integer,Question> questionDic;
	private int _remainingQuestion;
	private boolean showHiddenCategory = false;
	private Category _hiddenCategory;
	
	private Timestamp _startTime;
	private Timestamp _endTime;
	private boolean _isFinished = false;
	
	
	
	
	/**
	 * Constructor of the session class
	 * @param user
	 */
	public Session(User user) {
		_winnings = 0;
		_startTime = new Timestamp(System.currentTimeMillis());
		_user = user;
		questionDic = new HashMap<Integer, Question>();
	}
	
	
	
	/**
	 * when the sessions's game over method is called, the session is terminated 
	 * and all statistic that is associated with the player such as score is recorded 
	 * in the database.
	 */
	public void gameover() {
		_endTime = new Timestamp(System.currentTimeMillis());
		_isFinished = true;
	}
	
	
	/**
	 * Reset the current session to its initial state, 
	 */
	public void reset() {
		_startTime = new Timestamp(System.currentTimeMillis());
		_endTime = null;
		_winnings = 0;
		showHiddenCategory = false;
		for (Category cat : _cats) {
			for (Question question : cat.getQuestions()) {
				question.setAttempted(false);
			}
		}
		for (Question question: _hiddenCategory.getQuestions()) {
			question.setAttempted(false);
		}
	}
	
	/**
	 * Sets the question set for the game. Takes a list of categories and puts
	 * all the questions into a map corresponding to their questionID
	 * @param category
	 */
	public void setQuestionSet(List<Category> category) {
		_cats = category;
		_remainingQuestion = 0;
		for (Category cat: category) {
			for (Question question : cat.getQuestions()) {
				questionDic.put(question.getID(), question);
				if (!question.isAttempted()) {
					_remainingQuestion++;
				}
			}
		}
	}
	
	/**
	 * Sets the hidden category questions for the game. Takes a single category 
	 * as input and puts the questions into a map corresponding to their question ID
	 * @param hiddenCategory
	 */
	public void setHiddenCategory(Category hiddenCategory) {
		_hiddenCategory = hiddenCategory;
		for (Question question: hiddenCategory.getQuestions()) {
			questionDic.put(question.getID(), question);
		}
	}
	
	/*
	 *  ----------------------------------------------------------------------------
	 *  SESSION INFOMATION
	 *  The following methods are get method and set method for the current session
	 *  which related to the session information
	 *  
	 *  ----------------------------------------------------------------------------
	 */
	
	/**
	 * Get Method
	 * (YOU CAN change this to ID Depending on your implementaion of backend)
	 * @return the user associated with the session
	 */
	public User getUser() {
		return _user;
	}
	
	/**
	 * Get Method
	 * @return the session ID of the current class
	 */
	public int getSessionID() {
		return _ID;
	}
	
	/**
	 * Set method for ID,
	 * CAUTION!, this method is only meant to be called by database implementation and
	 * not anywhere of the application, throw illegalArgumentException if id already exist.
	 * @param id
	 * @throw IllegalArgumentException
	 */
	public void setSessionID(int id) {
		if (_ID == -1) {
			_ID = id;
		} else {
			throw new IllegalArgumentException("Id " + _ID + " already exist for sesssion: " + 
					_user.getName() + ". Cannot be set to id " + id +".\n");
		}

	}
	
	
	/**
	 * Get the question set from the session 
	 * @return category containing all question
	 */
	public List<Category> getCategoryList(){
		return _cats;
	}
	
	/**
	 * Get the current game over state of the session
	 * @return isFinsihed
	 */
	public boolean isFinished() {
		return _isFinished;
	}
	
	/**
	 * Increment the remaining question by the input number
	 * @param num
	 */
	public void incrementRemainingQuestion(int num) {
		_remainingQuestion += num;
	}
	/**
	 * Get Method
	 * @return the current remainingQuesiion of the session
	 */
	public int getRemainingQuestion() {
		return _remainingQuestion;	
	}
	
	/**
	 * Set Method, set the current remaining question of the session
	 * @param num
	 */
	public void setRemainingQuestion(int num) {
		_remainingQuestion = num;
	}
	
	/**
	 * Gets question based on its questionID
	 * @param id
	 * @return question
	 */
	public Question getQuestionById(int id) {
		return questionDic.get(id);
	}
	
	/**
	 * This method checks if the enough categories have been completed to
	 * unlock the hidden category and returns a boolean value.
	 * @return showHiddenCategory
	 */
	public boolean isShowHiddenCategory() {
		int catsCompleted = 0;
		for (Category cat: _cats) {
			int questionsAttempted = 0;
			
			for (Question question: cat.getQuestions()) {
				if (question.isAttempted()) {
					questionsAttempted++;
				}
			}
			
			if (questionsAttempted >= 5) {
				catsCompleted++;
			}
		}
		
		if (catsCompleted >= 2) {
			showHiddenCategory = true;
		}
		
		return showHiddenCategory;
	}
	/**
	 * 
	 * @param showHiddenCategory the showHiddenCategory to set
	 */
	public void setShowHiddenCategory(boolean showHiddenCategory) {
		this.showHiddenCategory = showHiddenCategory;
	}

	/**
	 * Gets the hidden category for the current game.
	 * @return
	 */
	public Category getHiddenCategory() {
		return _hiddenCategory;
	}
	
	
	/*
	 *  ----------------------------------------------------------------------------
	 *  USER STATISTIC
	 *  The following method are get method and set method for the current session
	 *  which related to user statistic of the session.
	 *  
	 *  ----------------------------------------------------------------------------
	 */
	
	/**
	 * Get Method, get creationTime of Session
	 * @return Time 
	 */

	public Timestamp getStartTime() {
		return _startTime;
	}
	
	/**
	 * Set Method, set creationTime of Session
	 * @param Time , time that session start
	 */

	public void setStartTime(Timestamp time) {
		_startTime = time;
	}
	
	/**
	 * Get Method, get finish time of Session
	 * @return Time 
	 */

	public Timestamp getFinishTime() {
		return _endTime;
	}
	
	/**
	 * Set Method, set finish Time of Session
	 * @param Time , time that session end
	 */
	public void setFinishTime(Timestamp time) {
		_endTime = time;
	}
	
	
	
	/**
	 * Get the playTime for the session;
	 * @return time in long?
	 */
	public long getPlayTime() {
		return _endTime.getTime() - _startTime.getTime();
	}
	
	/**
	 * Update wining
	 * @param value
	 */
	public void addWinnings(int value) {
		_winnings += value;
	}
	
	/**
	 * Set Method
	 * @param winnings, set the current winning of the session to a number;
	 */
	public void setWinnings(int winnings) {
		_winnings = winnings;
	}
	
	/**
	 * Get the current winning or score associated with the user for
	 * the current session.
	 * @return winning 
	 */

	public int getWinnings() {
		return _winnings;
	}

	
	/**
	 * Set Method, set is finish status of the session
	 * @param bool
	 */
	public void setIsFinished(boolean bool) {
		_isFinished = bool;
	}
	
	/**
	 * Utility method for printing all the field of session
	 */
	public void print() {
		System.out.printf(
				"session_id: %d, user_id: %d, score: %d, remaining_question: %d%n"
				+ "start_time: %s, finished_time: %s %n", 
				this.getSessionID(), this.getUser().getUserID(), this.getWinnings(), 
				this.getRemainingQuestion(), this.getStartTime().toString(), 
				String.valueOf(this.getFinishTime()).toString());
	}
	
	/**
	 * Utility method for printing all the question of the session.
	 */
	public void printCategoryList() {
		for (Category cat : _cats) {
			System.out.println(cat.getTitle() + " , id : " + cat.getCategoryID());
			for (Question question : cat.getQuestions()) {
				question.print();
			}
		}
	}

	

}

package quinzical.model;


import java.sql.Timestamp;
import java.util.ArrayList;
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
		for (Category cat : _cats) {
			for (Question question : cat.getQuestions()) {
				question.setAttempted(false);
			}
		}
	}
	
	public void setQuestionSet(List<Category> category) {
		_cats = category;
		_remainingQuestion = 0;
		for (Category cat: category) {
			for (Question question : cat.getQuestions()) {
				questionDic.put(question.getID(), question);
				_remainingQuestion++;
			}
		}
	}
	
	
	/*
	 *  ----------------------------------------------------------------------------
	 *  SESSION INFOMATION
	 *  The following method are get method and set method for the current session
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
	 * CAUTIOUS!, this method only meant to be called by database implementation and
	 * not Anywhere of the application, throw illegalArgumentException if id already exist.
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
	 * @return category containing all quetion
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
	public void setRemainingQuestoin(int num) {
		_remainingQuestion = num;
	}
	
	
	public Question getQuestionById(int id) {
		return questionDic.get(id);
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

	
	public void setIsFinished(boolean bool) {
		_isFinished = bool;
	}
	
	public void print() {
		System.out.printf(
				"session_id: %d, user_id: %d, score: %d, remaining_question: %d%n"
				+ "start_time: %s, finished_time: %s %n", 
				this.getSessionID(), this.getUser().getUserID(), this.getWinnings(), 
				this.getRemainingQuestion(), this.getStartTime().toString(), 
				String.valueOf(this.getFinishTime()).toString());
	}
	
	public void printCategoryList() {
		for (Category cat : _cats) {
			System.out.println(cat.getTitle() + " , id : " + cat.getCategoryID());
			for (Question question : cat.getQuestions()) {
				question.print();
			}
		}
	}

//	public void setActiveQuestion(Question question) {
//		_activeQuestion = question;
//		
//	}
//
//
//	public Question getActiveQuestion() {
//		return _activeQuestion;
//	}
	

}

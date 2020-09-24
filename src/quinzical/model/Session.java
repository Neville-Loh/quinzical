package quinzical.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to store the current session related to the user 
 * @author Neville
 *
 */

public class Session {
	

	private int _ID;
	private Timestamp _startTime;
	private Timestamp _endTime;
	private int _winnings;
	private User _user;
	private List<Question> _questions = new ArrayList<Question>();
	private boolean _isFinished = false;
	private ArrayList<Integer> _isAttempted;
	
	/**
	 * Session constructor.
	 */
	public Session(User user) {
		_winnings = 0;
		_startTime = new Timestamp(System.currentTimeMillis());
		_user = user;
	}

	/**
	 * Get Method, get creationTime of Session
	 * @return Time 
	 */

	public Timestamp getCreationTime() {
		return _startTime;
	}
	
	public void addWinnings(int value) {
		_winnings += value;
	}
	
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
	 * Get Method
	 * @return the session ID of the current class
	 */
	public int getSessionID() {
		return _ID;
	}
	
	/**
	 * Get Method
	 * (YOU CAN change this to ID Depending on your implementaion of backend)
	 * @return the user associated with the session
	 */
	public User getUser() {
		return _user;
	}
	
	public void addAttempted(Question question) {
		//_isAttempted.add(question.getID());
	}
	
	
	/**
	 * 
	 * @return
	 */

	public long getAttemptedMap() {
		// #TODO 
		return -1;
	}
	

	public boolean isFinished() {
		return _isFinished;
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
		for (Question question : _questions) {
			question.setAttempted(false);
		}
	}
	
	public long getPlayTime() {
		return _endTime.getTime() - _startTime.getTime();
	}
}

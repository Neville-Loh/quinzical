package quinzical.model;

/**
 * Class to store the current session related to the user 
 * @author Neville
 *
 */

public class Session {
	
	/**
	 * Get Method, get creationTime of Session
	 * @return Time 
	 */
	public long getCreationTime() {
		// #TODO 
		return -1;
	}
	
	/**
	 * Get the current winning or score associated with the user for
	 * the current session.
	 * @return winning 
	 */
	public int getWinning() {
		// #TODO 
		return -1;
	}
	
	/**
	 * Get Method
	 * @return the session ID of the current class
	 */
	public int getSessionID() {
		// #TODO
		return -1;
	}
	
	/**
	 * Get Method
	 * (YOU CAN change this to ID Depending on your implementaion of backend)
	 * @return the user associated with the session
	 */
	public User getUser() {
		// #TODO 
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public long getAtemptedMap() {
		// #TODO 
		return -1;
	}
	
	/**
	 * when the sessions's game over method is called, the session is terminated 
	 * and all statistic that is associated with the player such as score is recorded 
	 * in the database.
	 */
	public void gameover() {
		
	}
	
	/**
	 * Rest the current session to its initial state, 
	 */
	public void reset() {
		// #TODO 
	}
}

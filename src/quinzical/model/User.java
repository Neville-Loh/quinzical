package quinzical.model;

/**
 * Class that represent the user who is using the application,
 * Multiple instance of user can be created
 * @author Neville
 *
 */
public class User {
	

	private int _ID = -1;
	private String _name;
	private Session _currentSession;
	private int highestScore = 0;

	
	/**
	 * Constructor of the user, the user data such as Name is saved to database
	 * upon initialization.
	 */
	public User(String name) throws IllegalArgumentException {
		if (name.length() > 20) {
			throw new IllegalArgumentException();
		}
		_name = name;
		_currentSession = new Session(this);
	}
	
	
	/**
	 * Return the userId that is unique to each user
	 * @return The userID
	 */
	public int getUserID() {
		return _ID;
	}
	
	/**
	 * Set user id, this method only meant for database usage.
	 * If the id is not set, the id will become the input number
	 * Else throw IllegalArugment Exceptions.
	 * @param id
	 */
	public void setUserId(int id) {
		if (_ID == -1) {
			_ID = id;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Get Method, get the user name of the player
	 * @return name of the user
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Return the the latest session the user is in, a user can only be 
	 * associated with one session at the time.
	 * @return previous session of user
	 */
	public Session getSession() {
		return _currentSession;
		
	}
	
	public int getHighestScore() {
		return highestScore;
	}


	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	
	public void setSession(Session session) {
		_currentSession = session;
	}
	
	/**
	 * Utility method for printing user attribute
	 */
	public void print() {
		System.out.println("User name: " + _name + ",user_ID = " + _ID + ", highscore = " + highestScore);
		
	}
}

package quinzical.model;

/**
 * Class that represent the user who is using the application,
 * Multiple instance  of user can be creates 
 * @author Neville
 *
 */
public class User {
	

	private final int _ID;
	private String _name;
	private Session _currentSession;

	
	/**
	 * Constructor of the user, the user data such as Name is saved to database
	 * upon initialization.
	 */
	public User(String name, int ID) throws IllegalArgumentException {
		if (name.length() > 20) {
			throw new IllegalArgumentException();
		}
		_ID = ID;
		_name = name;
		_currentSession = new Session(this);
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * Return the userId that is unique to each user
	 * @return The userID
	 */
	public int getUserID() {
		return _ID;
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
	
	/**
	 * Clear all statistic that are associated with the user
	 * and reset the user to its initial status
	 */
	public void clearUserHistory() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Get all statistic associated to the user as an array which included
	 * DateTime, score pairs.
	 * @return
	 */
	public Object getUserStatistic() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSession(Session session) {
		_currentSession = session;
	}

}

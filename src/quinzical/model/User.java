package quinzical.model;

/**
 * Class that represent the user who is using the application,
 * Multiple instance  of user can be creates 
 * @author Neville
 *
 */
public class User {
	
	
	/**
	 * Constructor of the user, the user data such as Name is saved to database
	 * upon initialization.
	 */
	public User() {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * Return the userId that is unique to each user
	 * @return The userID
	 */
	public int getUserID() {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Get Method, get the user name of the player
	 * @return name of the user
	 */
	public String getname() {
		// TODO Auto-generated method stub
		return "ass";
	}
	
	/**
	 * Return the the latest session the user are in, one user can only be 
	 * associate to one session at the time.
	 * @return previous session of user
	 */
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
		
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
}

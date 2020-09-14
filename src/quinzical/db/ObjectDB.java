package quinzical.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Object implementation of a database. Use to save user data to the local directory
 * @author Neville
 */
public class ObjectDB implements Serializable {

	private static final long serialVersionUID = 1L;
    private Map<String, Boolean> _isAttempted;
    private int _winning;
    
    
    /**
     * Constructor, initialize winning as 0 and isAttempted as a empty
     * hashed map
     */
    public ObjectDB() {
    	_winning = 0;
    	_isAttempted = new HashMap<String, Boolean>();
    }
    
    /**
     * Get Method
     * @return score of user
     */
    public int getWinning() {
        return _winning;
    }
    /**
     * Get Method
     * @return attempted question as a boolean map
     */
    public Map<String, Boolean> getIsAttemptedMap() {
        return _isAttempted;
    }
    /**
     * Set Method
     * Set current sores of the user
     */
    public void setWinning(int winning) {
    	_winning = winning;
    }
    /**
     * Set Method
     * Set question attempted boolean map
     */
    public void setIsAttemptedMap(Map<String, Boolean> map) {
    	_isAttempted = map;
    }
}
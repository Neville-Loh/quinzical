package quinzical.db;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Object implementation of a database. Use to save user data to the local directory
 * @author Neville
 */
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String SAVE_FILENAME = "quinzical.config";
    private int lastUserId = -1;
    
    
    /**
     * Constructor, initialize winning as 0 and isAttempted as a empty
     * hashed map
     */
    public AppConfig() {
    }


	public int getLastUserId() {
		return lastUserId;
	}


	public void setLastUserId(int lastUserId) {
		this.lastUserId = lastUserId;
	}
    
	/**
	 * Save the config object as the save_file name at the system location.
	 * @param db
	 */
	public static void saveConfig(AppConfig config) {
		// Save Object
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILENAME))) {
			oos.writeObject(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Load the config object as the save_file name at the system location.
	 * @param db
	 */
	public static AppConfig loadconfig() {
		
		if (!configExist()) {
			return new AppConfig();
		}
		
		AppConfig config = null;
		// Read Object
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILENAME))) {
			config = (AppConfig) ois.readObject();
		} catch (Exception e) {
			System.out.println("config loading error");
			e.printStackTrace();
		}
		return config;
	}
	

	/**
	 * Check if config exists
	 * @return boolean
	 */
	public static boolean configExist() {
		File f = new File(SAVE_FILENAME);
		return (f.exists() && !f.isDirectory());
	}

}

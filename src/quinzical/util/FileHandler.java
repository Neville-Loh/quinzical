package quinzical.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import quinzical.model.Category;
import quinzical.model.Question;

/**
 * Utility class which handle all the loading and saving, writing logs file in
 * the Jeopardy application.
 * 
 * @author Neville
 *
 */
public class FileHandler {
	
	public static final String CATEGORY_FOLDER = "categories";
	public static final String SAVE_FILENAME = "user.save";
	
	
	/**
	 * Load category in the system location
	 * @param path of the folder.
	 * @return Category List which contain all the questions
	 */
	public static ArrayList<Category> loadCategory() {

		ArrayList<Category> result = new ArrayList<Category>();
		int score;
		String questionPrompt;
		String answer;

		try {
			String path = System.getProperty("user.dir") + File.separator + CATEGORY_FOLDER;
			System.out.println(String.format("Laoding path %s", path));
			File folder = new File(path);

			// Looping though each category
			for (File fileEntry : folder.listFiles()) {
				Category category = new Category(fileEntry.getName());
				BufferedReader br = new BufferedReader(new FileReader(fileEntry));

				// Read every line of category
				String line;
				while ((line = br.readLine()) != null) {
					String[] strList = line.split(",");

					score = Integer.parseInt(strList[0]);
					questionPrompt = strList[1];
					answer = strList[2];

					Question question = new Question(questionPrompt, answer, score);
					category.add(question);
				}

				result.add(category);
				br.close();
			}
			
		} catch (Exception e) {
			System.out.println("loadCategory error");
			e.printStackTrace();
		}

		return result;
	}
	
//	/**
//	 * Save the database object as the save_file name at the system location.
//	 * @param db
//	 */
//	public static void saveDB(ObjectDB db) {
//		// Save Object
//		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILENAME))) {
//			oos.writeObject(db);
//			System.out.println("DB has been saved");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	/**
//	 * Load the database object as the save_file name at the system location.
//	 * @param db
//	 */
//	public static ObjectDB loadDB() {
//		ObjectDB db = null;
//		// Read Object
//		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILENAME))) {
//			db = (ObjectDB) ois.readObject();
//		} catch (Exception e) {
//			System.out.println("DB loading error");
//			e.printStackTrace();
//		}
//		return db;
//	}
	
	/**
	 * Return true if the file name exist, else return false,
	 * The save_fileName;
	 * @return if the save file exist;
	 */
	public static boolean saveFileExist() {
		File f = new File(SAVE_FILENAME);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("Save file exist");
			return true;
		}
		System.out.println("Save file dne");
		return false;
	}

}

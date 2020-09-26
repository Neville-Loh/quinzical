package quinzical.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import quinzical.db.SQLiteDB;
import quinzical.model.Category;
import quinzical.model.Question;

/**
 * This class reads the categories, questions and answers from the provided txt file
 * into the database for use in the game.
 * 
 * @author danielcutfield
 *
 */

public class QuestionReader {
	
	private final String FILENAME;
	
	
	public QuestionReader(String location) {
		FILENAME = location;
	}
	
	public void populateCategoriesAndQuestions(SQLiteDB db) {
		try {
			File file = new File(FILENAME);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			Category cat = null;
			while ((line = br.readLine()) != null) {
				if (!line.equals("") && line.length() < 20) {
					cat = new Category(line);
					///
					System.out.println("adding category: " + cat.getTitle());
					db.addCategory(cat);
					////

					//db.addCategory(cat);
				} else if (!line.equals("")) {
					Question question = createQuestion(line);
					
					///
					
					System.out.printf("Propmt: %s %nPrefix: %s, Answer: %s %n", question.getPrompt(), question.getAnswerPrefix(),
							question.getAnswer());
					db.addQuestion(question, cat.getCategoryID());
					
					////
					cat.add(question);
					//db.addQuestion(question, catid - 1);
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Question createQuestion(String line) {
		int bracket = line.indexOf(")");
		String question = line.substring(0, line.indexOf(","));
		String prefix = line.substring(line.indexOf("(") + 1, bracket);
		String answer = line.substring(bracket + 1);
		question = removeSpaces(question);
		prefix = removeSpaces(prefix);
		answer = removeSpaces(answer);
		Question q = new Question(question, answer, prefix);
		return q;
	}
	
	private String removeSpaces(String str) {
		if (str.startsWith(" ")) {
			str = str.substring(1);
		}
		if (str.endsWith(" ")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

}

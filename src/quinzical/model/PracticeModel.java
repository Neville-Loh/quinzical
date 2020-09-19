package quinzical.model;

import java.util.ArrayList;

import quinzical.db.ObjectDB;
import quinzical.util.FileHandler;
import quinzical.util.Helper;

/**
 * THe Quiz model of the application. This class contain all the necessary
 * computation required by the quiz. The model is passed throughout all
 * controller.
 * 
 * @author Neville
 */

public class PracticeModel {
	private ArrayList<Category> _cats;
	private Category _activeCategory;
	private Question _activeQuestion;

	/**
	 * Constructor. The class initiate by loading all category. If there is also a
	 * save file in the directory, the model will load it too. If save file does not
	 * exist, all value will be set to its initial status
	 */
	public PracticeModel() {
		_cats = FileHandler.loadCategory();
		_activeCategory = _cats.get(1);
	}

	/**
	 * Perform game feature of answering question, the method checks if the answer
	 * is correct, update the score of the user, and return a boolean according to
	 * if the answer is correct or not.
	 * 
	 * @param question
	 * @param User input
	 * @return true if the answer is correct, else false
	 */
	public boolean answerQuestion(Question question, String input) {
		if (question.getAnswer().equalsIgnoreCase(input)) {
			return true;
		} else {
			return false;
		}

	}
	
	public void nextQuestion() {
		ArrayList<Question> questions = _activeCategory.getQuestions();
		setActiveQuestion(questions.get((int) (Math.random() * questions.size())));
	}


	/**
	 * TextToSpeech function using festival bash command.
	 * @param text to be turned into speach
	 */
	public void textToSpeech(String text) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String command = "echo \"" + text + "\" | festival --tts";
				Helper.runBash(command);
			}

		}).start();
	}

	/**
	 * Set Method. set the active question
	 * @param question
	 */
	public void setActiveQuestion(Question question) {
		_activeQuestion = question;
	}
	/**
	 * Get Method
	 * @return current selected question
	 */
	public Question getActiveQuestion() {
		return _activeQuestion;
	}
	/**
	 * Get Method
	 * @return all Category
	 */
	public ArrayList<Category> getCategoryList() {
		return _cats;
	}

}

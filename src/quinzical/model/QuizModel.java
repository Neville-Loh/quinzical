package quinzical.model;

import java.sql.SQLException;
import java.util.List;

import quinzical.db.QuinzicalDB;
import quinzical.db.SQLiteDB;
import quinzical.util.Espeak;
import quinzical.util.TextToSpeech;

/**
 * THe Quiz model of the application. This class contain all the necessary
 * computation required by the quiz. The model is passed throughout all
 * controller.
 * 
 * @author Neville, Daniel Cutfield
 */
public class QuizModel {

	private static QuizModel model;
	private QuinzicalDB db;
	private User _currentUser;
	private Session _currentSession;
	private Question _currentQuestion;
	private PracticeQuestion _practiceQuestion;
	
	private boolean _enableSpeech = true;
	private TextToSpeech tts = new Espeak();


	/**
	 * Singleton method to return the current model
	 * @return quiz model
	 */
	public static QuizModel getModel() {
		if (model == null) {
			model = new QuizModel();
			return model;
		} else {
			return model;
		}
	}

	/**
	 * Constructor. The class initiate by loading all category. If there is also a
	 * save file in the directory, the model will load it too. If save file does not
	 * exist, all value will be set to its initial status
	 */
	private QuizModel() {
		db = new SQLiteDB();

		try {
			db.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		_currentUser = new User("Default User");
		_currentUser.setUserId(99);
		loadUserSession();
		//_currentSession.printCategoryList();
	}

	/**
	 * Load the current user data using the fileHandler named user.save at the
	 * system directory. The object mimic a database
	 */
	public void loadUserSession() {
		_currentUser.print();
		_currentSession = db.getUserLastestSession(_currentUser);
		if (_currentSession == null) {
			initSession();
		}
	}

	/**
	 * Save the current session to database
	 */
	public void saveUserSession() {
		db.addSession(_currentUser, _currentSession);
	}

	/**
	 * Perform game feature of answering question, the method checks if the answer
	 * is correct, update the score of the user, and return a boolean according to
	 * if the answer is correct or not.
	 * 
	 * @param question
	 * @param User     input
	 * @return true if the answer is correct, else false
	 */
	public boolean answerQuestion(int qid, String input) {

		Question question = _currentSession.getQuestionById(qid);
		question.setAttempted(true);
		_currentSession.incrementRemainingQuestion(-1);
		if (validate(input, question)) {
			_currentSession.addWinnings(question.getScore());
			return true;
		} else {
			// _winning -= question.getScore();
			return false;
		}

	}

	/**
	 * This function validates the answer input by the user. It also deals with any
	 * macrons that may be present in the answer.
	 * 
	 * @param userInput
	 * @param question
	 * @return true if answer is correct, else false
	 */

	public boolean validate(String userInput, Question question) {
		String answer = question.getAnswer();
		String prefix = question.getAnswerPrefix();
		int i = 0;
		while (true) {
			if (userInput.equalsIgnoreCase(answer) || userInput.equalsIgnoreCase(prefix + " " + answer)) {
				return true;
			} else if (userInput.startsWith(" ")) {
				userInput = userInput.substring(1);
			} else if (userInput.endsWith(" ") || userInput.endsWith(".")) {
				userInput = userInput.substring(0, userInput.length() - 1);
			} else if (i == 0) {
				answer = replaceMacrons(answer);
				i++;
			} else if (i == 1) {
				userInput = userInput.toLowerCase();
				if (userInput.startsWith("the ")) {
					userInput = userInput.substring(4);
				} else if (userInput.startsWith("a ")) {
					userInput = userInput.substring(2);
				}
				i++;
			} else if (i == 2) {
				if (answer.contains("/")) {
					String[] answers = answer.split("/");
					for (int j = 0; j < answers.length; j++) {
						if (userInput.equalsIgnoreCase(answers[j]) || userInput.equalsIgnoreCase(prefix + " " + answers[j])) {
							return true;
						}
					}
				}
				i++;
			} else {
				return false;
			}
		}
	}

	/**
	 * Replaces letters with macrons present in a string with their equivalent
	 * regular characters. Helper function used in validate().
	 * 
	 * @param str
	 * @return str
	 */

	private String replaceMacrons(String str) {
		str = str.replace("ā", "a");
		str = str.replace("ē", "e");
		str = str.replace("ī", "i");
		str = str.replace("ō", "o");
		str = str.replace("ū", "u");
		return str;
	}

	/**
	 * Count and update the question remaining, the function loop though all
	 * questions in the category and count the attempted function.
	 */
	public void updateRemainingQuestion() {
		_currentSession.setRemainingQuestion(0);
		for (Category cat : _currentSession.getCategoryList()) {
			for (Question question : cat.getQuestions()) {
				if (!question.isAttempted()) {
					_currentSession.incrementRemainingQuestion(1);
				}
			}
		}
	}

	/**
	 * Save the current user data to a file named user.save at The system directory
	 * The file is saved as an object which mimic a database
	 */
	public void save() {
		System.out.println("Saving Session...");
		db.addSession(_currentUser, _currentSession);
	}

	/**
	 * Rest the game of the session, the user score is reset to 0 and all attempted
	 * question will be set to its non attempted status
	 */
	public void reset() {
		_currentSession.reset();
		_currentSession.setRemainingQuestion(0);
		for (Category cat : _currentSession.getCategoryList()) {
			for (Question question : cat.getQuestions()) {
				question.setAttempted(false);
				_currentSession.incrementRemainingQuestion(1);
			}
		}
	}

	/**
	 * TextToSpeech function using espeak bash command.
	 * 
	 * @param text to be turned into speech
	 */
	public void textToSpeech(String text) {
		if (_enableSpeech) {
			tts.start(text);
		}
	}
	
	/**
	 * Set the speed of text to speech
	 * @param speed
	 */
	public void setSpeechSpeed(int speed) {
		tts.setSpeed(speed);
	}
	
	/**
	 * Set the volume of text to speech
	 * @param volume
	 */
	public void setSpeechVolume(int volume) {
		tts.setVolume(volume);
	}

	/**
	 * Set Method. set the active question
	 * @param question
	 */
	public void setActiveQuestion(Question question) {
		_currentQuestion = question;
	}

	/**
	 * Get Method
	 * @return current selected question
	 */
	public Question getActiveQuestion() {
		return _currentQuestion;

	}

	/**
	 * Get Method
	 * @return all Category
	 */
	public List<Category> getCategoryList() {
		return _currentSession.getCategoryList();
	}

	/**
	 * Get Method
	 * @return score of the player
	 */
	public int getWinning() {
		return _currentSession.getWinnings();
	}

	/**
	 * Get Method
	 * @return Score as a string with dollar sign
	 */
	public String getWinningStr() {
		return "$" + Integer.toString(_currentSession.getWinnings());
	}

	/**
	 * Get Method
	 * @return total question left
	 */
	public int getRemainingQuestionCount() {
		return _currentSession.getRemainingQuestion();
	}

	/**
	 * Select a random practice question in the model.
	 * @param categoryId
	 */
	public void selectRandomPracticeQuestion(int categoryId) {
		_practiceQuestion = new PracticeQuestion(db.getRandomQuestionFromCategory(categoryId));
		_currentQuestion = _practiceQuestion;

	}

	/**
	 * Get Method
	 * @return the current practice question of the model
	 */
	public PracticeQuestion getCurrentPracticeQuestion() {
		return _practiceQuestion;
	}

	/**
	 * Method to answer a practice question
	 * @param input, user input text
	 * @return boolean if the answer is correct
	 */
	public boolean answerPracticeQuestion(String input) {
		if (validate(input, _practiceQuestion)) {
			return true;
		} else {
			int attemptLeft = _practiceQuestion.getAttemptLeft() - 1;
			_practiceQuestion.setAttemptLeft(attemptLeft);
			return false;
		}

	}

	/**
	 * Create new user and add user to db
	 * @param name of the user
	 * @return userId if successful
	 */
	public int createNewUser(String name) {
		User user = new User(name);
		db.addUser(user);
		return user.getUserID();
	}

	/**
	 * Get the current user of the model
	 * @return user object
	 */
	public User getUser() {
		return _currentUser;
	}

	/**
	 * Set the current user of the model
	 * @param userid
	 */
	public void setUser(int userid) {
		// change user to user id;
		User user = db.getUser(userid);
		_currentUser = user;

		// load current most recent user session;
		loadUserSession();

	}
	
	/**
	 * Set the current user of the model
	 * @param userid
	 */
	public void setUser(User user) {
		// change user to user id;
		_currentUser = user;
		// load current most recent user session;
		loadUserSession();

	}

	/**
	 * create a new Session for the user
	 */
	public void createNewSession() {
		// Creating new session which associate to current user.
		Session session = new Session(_currentUser);
		session.setQuestionSet(db.getRandomQuestionSet(5, 5));
	}

	/**
	 * Get Category list which contain category with no question
	 * @return Category List
	 */
	public List<Category> getAllCategorywithoutQuestion() {
		return db.getAllCategory();
	}

	/**
	 * Get the current session of the model The return reference contain all
	 * question that are store in the sesions.
	 * @return Session object
	 */
	public Session getSession() {
		return _currentSession;
	}

	/**
	 * initiate a new session if the current session is null;
	 */
	public void initSession() {
		_currentSession = new Session(_currentUser);
		List<Category> cat = db.getRandomQuestionSet(5, 5);
		_currentSession.setQuestionSet(cat);

	}

	/**
	 * Finish the current session, and get a new one session for the current user
	 */
	public void finishCurrentSession() {
		_currentSession.setIsFinished(true);
		db.addSession(_currentUser, _currentSession);
		_currentSession = new Session(_currentUser);
		List<Category> cat = db.getRandomQuestionSet(5, 5);
		_currentSession.setQuestionSet(cat);
	}
	
	/**
	 * Get method
	 * @return db;
	 */
	public QuinzicalDB getDb() {
		return db;
	}
	/**
	 * set method, set enable speech to a boolean
	 * @param true/false
	 */
	public void setEnableSpeech(boolean b) {
		_enableSpeech = b;

	}
	
	/**
	 * Get method, get if the model current enable speech
	 * @return _enableSpeech
	 */
	public boolean isEnableSpeech() {
		return _enableSpeech;
	}
	
	public TextToSpeech getTextToSpeechObject() {
		return tts;
	}

}

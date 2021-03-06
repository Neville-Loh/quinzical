package quinzical.model;

import java.sql.SQLException;
import java.util.List;

import quinzical.db.AppConfig;
import quinzical.db.QuinzicalDB;
import quinzical.db.SQLiteDB;
import quinzical.util.Espeak;
import quinzical.util.TextToSpeech;

/**
 * The Quiz model of the application. This class contain all the necessary
 * computation required by the quiz. The model is passed throughout all
 * controller.
 * 
 * @author Neville, Daniel
 */
public class QuizModel {

	private static QuizModel model;
	private QuinzicalDB db;
	private User _currentUser;
	private Session _currentSession;
	private Question _currentQuestion;
	private PracticeQuestion _practiceQuestion;
	
	private TextToSpeech tts = new Espeak();
	private AppConfig config = AppConfig.loadconfig();


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
	 * Constructor. The class initiates by loading all categories. If there is also a
	 * save file in the directory, the model will load that too. If a save file does not
	 * exist, all values will be set to their initial status
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
	}

	/**
	 * Load the current user data using the fileHandler named user.save at the
	 * system directory. The object mimics a database
	 */
	public void loadUserSession() {
		_currentSession = db.getUserLastestSession(_currentUser);
		if (_currentSession == null) {
			initSession();
		}
	}

	/**
	 * Perform game feature of answering a question, the method checks if the answer
	 * is correct, updates the score of the user, and returns a boolean according to
	 * if the answer is correct or not.
	 * 
	 * @param question
	 * @param input
	 * @return true if the answer is correct, else false
	 */
	public boolean answerQuestion(int qid, String input) {

		Question question = _currentSession.getQuestionById(qid);
		question.setAttempted(true);
		_currentSession.incrementRemainingQuestion(-1);
		Category hiddenCategory = _currentSession.getHiddenCategory();
		for (Question hiddenQuestion: hiddenCategory.getQuestions()) {
			if (hiddenQuestion.equals(question)) {
				_currentSession.incrementRemainingQuestion(1);
				break;
			}
		}
		if (validate(input, question)) {
			_currentSession.addWinnings(question.getScore());
			return true;
		} else {
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

	private boolean validate(String userInput, Question question) {
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
		str = str.replace("??", "a");
		str = str.replace("??", "e");
		str = str.replace("??", "i");
		str = str.replace("??", "o");
		str = str.replace("??", "u");
		return str;
	}

	/**
	 * Count and update the number of questions remaining, the function loops though all
	 * questions in the category and counts the attempted questions.
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
	 * The file is saved as an object which mimics a database
	 */
	public void save() {
		db.addSession(_currentUser, _currentSession);
		config.setLastUserId(_currentUser.getUserID());
		AppConfig.saveConfig(config);
	}

	/**
	 * Reset the game of the session, the user score is reset to 0 and all attempted
	 * question will be set to its non attempted status
	 */
	public void reset() {
		_currentSession.reset();
		_currentSession.setRemainingQuestion(0);
		_currentSession.setQuestionSet(db.getRandomQuestionSet(5, 5));
	}

	/**
	 * TextToSpeech function using espeak bash command.
	 * @param text to be turned into speech
	 */
	public void textToSpeech(String text) {
		if (config.isEnableSpeech()) {
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
		session.setHiddenCategory(db.getInternationalQuestionSet(5));
	}

	/**
	 * Get Category list which contain category with no questions
	 * @return Category List
	 */
	public List<Category> getAllCategorywithoutQuestion() {
		return db.getAllCategory();
	}

	/**
	 * Get the current session of the model The return reference contain all
	 * question that are stored in the sessions.
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
		_currentSession.setHiddenCategory(db.getInternationalQuestionSet(5));

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
		_currentSession.setHiddenCategory(db.getInternationalQuestionSet(5));
	}
	
	
	public AppConfig getConfig() {
		return config;
	}
	/**
	 * Get method
	 * @return db;
	 */
	public QuinzicalDB getDb() {
		return db;
	}
	
	/**
	 * returns the text to speech object
	 * @return
	 */
	public TextToSpeech getTextToSpeechObject() {
		return tts;
	}
	
	/**
	 * Sets the current user as the last user of the app
	 */
	public void setUserAsLastUser() {
		int id = config.getLastUserId();
		if (id == -1) {
			setUser(1);
		} else {
			setUser(id);
		}
	}
	
}

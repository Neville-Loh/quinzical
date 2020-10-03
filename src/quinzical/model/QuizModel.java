package quinzical.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quinzical.db.QuinzicalDB;
import quinzical.db.SQLiteDB;
import quinzical.util.Festival;
//import quinzical.db.QuinzicalDB;
import quinzical.util.FileHandler;
import quinzical.util.Helper;
import quinzical.util.TextToSpeech;
import test.testSQLiteDB;

/**
 * THe Quiz model of the application. This class contain all the necessary
 * computation required by the quiz. The model is passed throughout all
 * controller.
 * 
 * @author Neville
 */
public class QuizModel {
	private User _currentUser;
	private Session _currentSession;
	private QuinzicalDB db;
	private TextToSpeech tts;

	private PracticeQuestion _practiceQuestion;
	private Question _currentQuestion;
//	private GameMode gameMode = GameMode.normal;
//	


	
	

	/**
	 * Constructor. The class initiate by loading all category. If there is also a
	 * save file in the directory, the model will load it too. If save file does not
	 * exist, all value will be set to its initial status
	 */
	public QuizModel() {
		
		System.out.println("Quiz Model init");
		db = new SQLiteDB();
		//tts = new Festival();
		
		try {
			db.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		_currentUser = new User("default");
		_currentUser.setUserId(99);
		_currentSession = new Session(_currentUser);
		List<Category> cat = db.getRandomQuestionSet(5, 5);
		_currentSession.setQuestionSet(cat);
		//db.getUserSession(1);
		
//		_cats = FileHandler.loadCategory();
//		updateRemainingQuestion();
//		try {
//			if (FileHandler.saveFileExist()) {
//				load();
//				System.out.println("load file suscessfuly load");
//			}
//		} catch (Exception e) {
//			System.out.println("Loading failed. Maybe trying removing user.save in the working directory");
//			e.printStackTrace();
//		}
	}
	
	
	public void loadUserSession() {
		_currentSession = db.getUserLastestSession(_currentUser); 
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
	 * @param User input
	 * @return true if the answer is correct, else false
	 */
	public boolean answerQuestion(int qid , String input) {
		
		Question question = _currentSession.getQuestionById(qid);
		question.setAttempted(true);
		_currentSession.setRemainingQuestoin(_currentSession.getRemainingQuestion() - 1);
		if (validate(input, question)) {
			_currentSession.addWinnings(question.getScore());
			return true;
		} else {
			//_winning -= question.getScore();
			return false;
		}

	}
	
	/**
	 * This function validates the answer input by the user. It also deals with any macrons
	 * that may be present in the answer.
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
			} else if (i == 0) {
				answer = replaceMacrons(answer);
				i = 1;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Replaces letters with macrons present in a string with their equivalent regular characters.
	 * Helper function used in validate().
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
		_currentSession.setRemainingQuestoin(0);
		for (Category cat : _currentSession.getCategoryList()) {
			for (Question question : cat.getQuestions()) {
				if (!question.isAttempted()) {
					_currentSession.setRemainingQuestoin(_currentSession.getRemainingQuestion() + 1);
				}
			}
		}
	}

	/**
	 * Save the current user data to a file named user.save at The system directory
	 * The file is saved as an object which mimic a database
	 */
	public void save() {
//		ObjectDB db = new ObjectDB();
//		db.setWinning(_currentSession.getWinnings());
//		HashMap<String, Boolean> isAttemptedMap = new HashMap<String, Boolean>();
//		for (Category cat : _currentSession.getCategoryList()) {
//			for (Question question : cat.getQuestions()) {
//				isAttemptedMap.put(question.toString(), question.isAttempted());
//			}
//		}
//		db.setIsAttemptedMap(isAttemptedMap);
//		FileHandler.saveDB(db);
	}

	/**
	 * Load the current user data using the fileHandler named user.save at the
	 * system directory. The object mimic a database
	 */
	public void load() {
//		ObjectDB db = FileHandler.loadDB();
//		_currentSession.setWinnings(db.getWinning());
//		Map<String, Boolean> isAttemptedMap = db.getIsAttemptedMap();
//
//		// TODO handle exception
//		for (Category cat : _currentSession.getCategoryList()) {
//			for (Question question : cat.getQuestions()) {
//				if (isAttemptedMap.get(question.toString()) == true) {
//					question.setAttempted(true);
//				}
//			}
//
//		}
//		updateRemainingQuestion();
	}

	/**
	 * Rest the game of the session, the user score is reset to 0 and all attempted
	 * question will be set to its non attempted status
	 */
	public void reset() {
		_currentSession.reset();
		_currentSession.setRemainingQuestoin(0);
		for (Category cat : _currentSession.getCategoryList()) {
			for (Question question : cat.getQuestions()) {
				question.setAttempted(false);
				_currentSession.setRemainingQuestoin(_currentSession.getRemainingQuestion() + 1);
			}
		}
	}

	/**
	 * TextToSpeech function using festival bash command.
	 * @param text to be turned into speach
	 */
	public void textToSpeech(String text) {
		//tts.start(text);
		System.out.println("TTS called: " + text);
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

	
	// -------------------------- New implemented method -------------------------
	public void selectRandomPracticeQuestion(int categoryId) {
		_practiceQuestion = new PracticeQuestion(db.getRandomQuestionFromCategory(categoryId));
		testSQLiteDB.printQuestion(_practiceQuestion);
		_currentQuestion = _practiceQuestion;
		
	}


	public PracticeQuestion getCurrentPracticeQuestion() {
		return _practiceQuestion;
	}


	public boolean answerPracticeQuestion(String input) {
		if (validate(input, _practiceQuestion)) {
			return true;
		} else {
			int attemptLeft = _practiceQuestion.getAttemptLeft() - 1;
			_practiceQuestion.setAttemptLeft(attemptLeft);
			return false;
		}
		
	}


	public int createNewUser(String string) {
		User user = new User(string);
		db.addUser(user);
		return user.getUserID();
	}


	public void setUser(int userid) {
		// change user to user id;
		User user = db.getUser(userid);
		_currentUser = user;
		
		// load current most recent user session;
		Session session = db.getUserLastestSession(user);
		_currentSession = session;
		
	}


	public void createNewSession() {
		// Creating new session which associate to current user.
		Session session = new Session(_currentUser);
		session.setQuestionSet(db.getRandomQuestionSet(5, 5));
	}

	
	public List<Category> getAllCategorywithoutQuestion(){
		return db.getAllCategory();
	}

	public void setActiveQuestion(int id) {
		// TODO Auto-generated method stub
		
	}


	public int getPracticeAttemptLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

//
//	public GameMode getGameMode() {
//		return gameMode;
//	}
//
//
//	public void setGameMode(GameMode gameMode) {
//		this.gameMode = gameMode;
//	}



}

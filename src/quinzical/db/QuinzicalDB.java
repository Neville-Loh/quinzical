package quinzical.db;

import java.sql.SQLException;
import java.util.List;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;

/**
 * Interface for data base required functions for the application  
 * @author Neville
 */
public interface QuinzicalDB {
	
	
	public void getConnection() throws ClassNotFoundException, SQLException;
	/*
	 * =====================================================================================================
	 * USER
	 * Methods related to user
	 * =====================================================================================================
	 */
	
	/**
	 * Retrieve the user object in the database.
	 * @param id, the id of the user
	 * @return user, a user object with id attached to it
	 */
	public User getUser(int id);
	
	/**
	 * Get a List of all user in the database
	 * all user will have an id unique to them.
	 * @return List of user
	 */
	public List<User> getAllUser();
	
	/**
	 * Update the user object in the database if any changes have been made to the
	 * object
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * Add a user to data base,
	 * After this method called, the input object, user, will be assigned a user 
	 * id, since the input is passed in by reference, after the method call method
	 * user.getUserId() can be called which can retrieve the automatically 
	 * assigned id from the database.
	 * @param user, the user object
	 */
	public void addUser(User user);
	
	/**
	 * Delete a user from the database.
	 * All session related to user will be deleted in a cascade fashion
	 * @param userId
	 */
	public void deleteUser(int userId);
	
	
	/*
	 * =====================================================================================================
	 * SESSION
	 * Methods related to session
	 * =====================================================================================================
	 */
	
	/**
	 * Get the latest session of the user. The session must be unfinished,
	 * if there are no unfinished session, return null.
	 * 
	 * The session will be assigned all previous value, including the question 
	 * set related to the session, the user score, and the session ID,
	 * 
	 * @param userId
	 * @return session object
	 */
	public Session getUserLastestSession(User user);
	
	/**
	 * Add a Session to the database, 
	 * After this method called, the input object, session, will be assigned a session 
	 * id, since the input is passed in by reference, after the method call method
	 * session.getId() can be called which can retrieve the automatically 
	 * assigned id from the database.
	 * 
	 * @param user which process the session
	 * @param session, the session to be saved
	 */
	public void addSession(User user, Session session);
	
	/**
	 * Delete a session, session entry will be deleted
	 * @param sessionId
	 */
	public void deleteSession(int sessionId);
	
	/*
	 * =====================================================================================================
	 * CATEGORY 
	 * Methods related to category
	 * =====================================================================================================
	 */
	
	/**
	 * Returns an new category object with the name matching the id provided
	 * but without any questions in it
	 * @param categoryId
	 * @return
	 */
	public Category getEmptyCategory(int categoryId);
	
	/**
	 * Adds a category to the database
	 * @param category
	 */
	public void addCategory(Category category);
	
	/**
	 * Deletes the category from the database with the matching category id
	 * @param categoryId
	 */
	public void deleteCategory(int categoryId);
	

	/**
	 * Get a random QuestionSet as List of Category
	 * The method return a category list where categoryCount is the number of categories which 
	 * are selected randomly, and the questionCount is the number of questions within 
	 * each category
	 * 
	 * @param categoryCount, 
	 * @param questionCount
	 * @return
	 */
	List<Category> getRandomQuestionSet(int categoryCount, int questionCount);
	
	/**
	 * Get a random set of questions for the hidden international category
	 * Returns a category object containing the randomly selected international questions
	 * @param questionCount
	 * @return
	 */
	public Category getInternationalQuestionSet(int questionCount);
	
	/**
	 * Retrieve all the question and category from the database
	 * @return
	 */
	public List<Category> getAllCategory();
	
	/*
	 * =====================================================================================================
	 * QUESTION
	 * Methods related to question
	 * =====================================================================================================
	 */
	
	/**
	 * Get a single question using the question id
	 * @param questionId
	 * @return
	 */
	public Question getQuestion(int questionId);
	
	/**
	 * Get a random question from a category
	 * @param categoryId, id of the category
	 * @return question
	 */
	public Question getRandomQuestionFromCategory(int categoryId);
	
	/**
	 * Add a question to the database
	 * @param quesiton
	 * @param categoryId
	 */
	public void addQuestion(Question quesiton, int categoryId);
	
	/**
	 * delete a question from the database, all session entry that contain the question
	 * should be deleted accordingly
	 * @param questionId
	 */
	public void deleteQuestion(int questionId);

	
}

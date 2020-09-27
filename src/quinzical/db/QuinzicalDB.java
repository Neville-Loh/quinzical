package quinzical.db;

import java.util.ArrayList;
import java.util.List;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;

/**
 * Interface for data base required functions for the application  
 * @author Neville
 *
 */
public interface QuinzicalDB {

	/*
	 * ------------------------------------------------------------------------
	 * USER
	 * Method related to user
	 * ------------------------------------------------------------------------
	 */
	
	/**
	 * Retrieve the user object in the database.
	 * @param id
	 * @return
	 */
	public User getUser(int id);
	
	/**
	 * Get a List of all User
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 
	 * @param userId
	 */
	public void deleteUser(int userId);
	
	/*
	 * ------------------------------------------------------------------------
	 * Session
	 * Method related to user
	 * ------------------------------------------------------------------------
	 */
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Session getUserSession(int userId);
	
	public void addSession(User user, Session session);
	
	public void deleteSession(int sessionId);
	
	/*
	 * ------------------------------------------------------------------------
	 * USER
	 * Method related to user
	 * ------------------------------------------------------------------------
	 */
	
	public Category getCategory(int categoryId);
	
	public List<Category> getAllCategory();
	
	public void addCategory(Category category);
	
	public void deleteCategory(int categoryId);
	
	public int getCategoryId(String CategoryId);
	
	List<Category> getRandomQuestionSet(int categoryCount, int questionCount);
	
	/*
	 * ------------------------------------------------------------------------
	 * USER
	 * Method related to user
	 * ------------------------------------------------------------------------
	 */
	public Question getQuestion(int questionId);
	
	public void addQuestion(Question quesiton, int categoryId);
	
	public void deleteQuestion(int questionId);

	
}

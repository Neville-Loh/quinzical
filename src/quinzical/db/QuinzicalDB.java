package quinzical.db;

import java.util.ArrayList;
import java.util.List;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;

public interface QuinzicalDB {

	/**
	 * End
	 * @param id
	 * @return
	 */
	public User getUser(int id);
	
	public List<User> getAllUser();
	
	public void addUser(User user);
	
	public void deleteUser(int userId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	
	public Session getUserSession(int userId);
	
	public void addSession(User user, Session session);
	
	public void deleteSession(int sessionId);
	
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	
	public Category getCategory(int categoryId);
	
	public List<List<Category>> getAllCategory();
	
	public void addCategory(String categoryName);
	
	public void deleteCategory(int categoryId);
	
	/**
	 * 
	 * @param questionId
	 * @return
	 */
	
	public Question getQuestion(int questionId);
	
	public void addQuestion(Question quesiton, int categoryId);
	
	public void deleteQuestion(int questionId);
	
}

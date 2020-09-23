package quinzical.db;

import java.util.ArrayList;
import java.util.List;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;

public interface QuinzicalDB {

	
	public List<ArrayList<Category>> getAllCategory();
	
	public User getUser(int id);
	
	public Session getUserSession(int UserId);
	
	public void addCategory(String categoryName);
	
	public void addQuestion(Question quesiton);
	
	public void addUser(User user);
	
	public void deleteUser(int userId);
	
	public void create();
}

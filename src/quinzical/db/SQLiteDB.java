package quinzical.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;

/**
 * 
 * @author Neville
 *
 */
public class SQLiteDB implements QuinzicalDB{
	
	private static Connection conn = null;
	private static boolean hasData = false;
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if(conn == null) {
			getConnection();
		}
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT user_name From user");
		return res;
	}

	public void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);  
        conn = DriverManager.getConnection("jdbc:sqlite:SQLiteQuinzical.db",config.toProperties());
		initialise();
	}

	private void initialise() throws SQLException {
		if( !hasData ) {
			hasData = true;
			
			SQLiteSchema.createUserTable(conn);
			SQLiteSchema.createCategoryTable(conn);
			SQLiteSchema.createQuestionTable(conn);
			SQLiteSchema.createSessionTable(conn);
		}
	}
	
	/**
	 * User
	 * Implementation of all end point method of the related
	 * to the class User
	 */
	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User user) {
		PreparedStatement prep = null;
			try {
				prep = conn.prepareStatement("INSERT INTO user(user_name) values(?);");
				prep.setString(1, user.getName());
				prep.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} 
	}

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Session
	 * Implementation of all end point method of the related
	 * to the class Session 
	 */
	@Override
	public Session getUserSession(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSession(User user, Session session) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement("INSERT INTO session(user_id, score, isFinished, finished_time) values(?,?,?,?);");
			prep.setInt(1, user.getUserID());
			prep.setInt(2, session.getWinnings());
			prep.setBoolean(3, session.isFinished());
			prep.setTimestamp(4, session.getCreationTime());
			prep.execute();
			try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	int sessionId = generatedKeys.getInt(1);
	                //user.setUserId(generatedKeys.getInt(1));
	                System.out.println("The user " + user.getName() + " have id " + sessionId);
	                
		            // saving each question in 
		            List<Category> cats = session.getCategoryList();
		            for (Category cat : cats) {
		            	for (Question question : cat.getQuestions()) {
		            		prep = conn.prepareStatement("REPLACE INTO session_questions("
		            				+ "session_id, question_id, isAtempted) values(?,?,?);");
		            		prep.setInt(1, sessionId);
		            		prep.setInt(1, question.getID());
		            		prep.setBoolean(3, question.isAttempted());
		            	}
		            }
	            } else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	            
	            
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void deleteSession(int sessionId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Category
	 * Implementation of all end point method of the related
	 * to the class Category
	 */

	@Override
	public Category getCategory(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Category> getRandomQuestionSet(List<String> categoryNames) {
		List<Category> result = new ArrayList<Category>();
		
		for (String categoryName : categoryNames) {
			Category cat = new Category(categoryName);
			
			// Adding new question 
			PreparedStatement prep = null;
			try {
				prep = conn.prepareStatement("SELECT question_id, prompt, answer FROM question "
						+ "WHERE category_id = "
						+ cat.getCategoryId()
						+ " ORDER by RANDOM() LIMIT 5;");
				ResultSet res = prep.executeQuery();
				
				while( res.next() ) {
					System.out.println(res);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			result.add(cat);
		}
		return result;
		
	}

	@Override
	public void addCategory(Category category) {
		PreparedStatement prep = null;
			try {
				prep = conn.prepareStatement("INSERT INTO category(category_name) values(?);");
				prep.setString(1, category.getTitle());
				prep.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
					
	}

	@Override
	public void deleteCategory(int categoryId) {
		DbUtils.deleteEntryInTable(conn, categoryId, "category");
		
	}
	
	/**
	 * Question
	 * Implementation of all end point method of the related
	 * to the class Question
	 */
	
	@Override
	public Question getQuestion(int questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addQuestion(Question quesiton, int categoryId) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement("INSERT INTO question(prompt,answer, category_id) VALUES(?,?,?);");
			prep.setString(1, quesiton.toString());
			prep.setString(2, quesiton.getAnswer());
			prep.setInt(3, categoryId);
			prep.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void deleteQuestion(int questionId) {
		// TODO Auto-generated method stub
		
	}
	

}

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
		// TODO Auto-generated method stub
		Class.forName("org.sqlite.JDBC");
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);  
        conn = DriverManager.getConnection("jdbc:sqlite:SQLiteQuinzical.db",config.toProperties());
		initialise();
	}

	private void initialise() throws SQLException {
		// 
		if( !hasData ) {
			hasData = true;
			
			//SQLiteSchema.createUserTable(conn);
			SQLiteSchema.createCategoryTable(conn);
			SQLiteSchema.createQuestionTable(conn);
			//SQLiteSchema.createSessionTable();
			

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
		if(conn == null) {
			try {
				getConnection();
				prep = conn.prepareStatement("INSERT INTO user(user_name) values(?);");
				prep.setString(1, user.getName());
				prep.execute();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				
			} 
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
		// TODO Auto-generated method stub
		
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
	public List<List<Category>> getAllCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCategory(String categoryName) {
		PreparedStatement prep = null;
			try {
				prep = conn.prepareStatement("INSERT INTO category(category_name) values(?);");
				prep.setString(1, categoryName);
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

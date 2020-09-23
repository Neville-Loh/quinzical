package quinzical.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	private static Connection conn;
	private static boolean hasData = false;
	
	private static int USERNAME_CHAR_LIMIT = 20;
	private static int CATEGORYNAME_CHAR_LIMIT = 50;
	private static int QUESTION_CHAR_LIMIT = 200;
	
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if(conn == null) {
			getConnection();
		}
		
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT user_name From user");
		return res;
	}

	private void getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:SQLiteQuinzical.db");
		initialise();
	}

	private void initialise() throws SQLException {
		// 
		Statement state = conn.createStatement();
		state.executeQuery("PRAGMA foreign_keys = ON");		
		if( !hasData ) {
			hasData = true;
			
			SQLiteSchema.createUserTable(conn);
			//SQLiteSchema.createQuestionTable();
			//SQLiteSchema.createSessionTable();
			//SQLiteSchema.createCategoryTable();

		}
	}
	
	
	
	
	
	@Override
	public List<ArrayList<Category>> getAllCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getUserSession(int UserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addQuestion(Question quesiton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		if(conn == null) {
			try {
				getConnection();
				PreparedStatement prep = conn.prepareStatement("INSERT INTO user(username) values(?);");
				prep.setString(1, user.getname());
				prep.execute();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	
		
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCategory(String categoryName) {
		if(conn == null) {
			try {
				getConnection();
				PreparedStatement prep = conn.prepareStatement("INSERT INTO user(username) values(?);");
				prep.setString(1, categoryName);
				prep.execute();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	
		
	}

}

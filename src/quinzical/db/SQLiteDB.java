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
			//SQLiteSchema.createQuestionTable(conn);
			//SQLiteSchema.createSessionTable();
			

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
	public void addUser(User user) {
		PreparedStatement prep = null;
		if(conn == null) {
			try {
				getConnection();
				prep = conn.prepareStatement("INSERT INTO user(user_name) values(?);");
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

}

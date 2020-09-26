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

public class SQLiteDB implements QuinzicalDB{
	
	private  static Connection con;
	private  static boolean hasData = false;
	
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT username From user");
		return res;
	}

	private void getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
		initialise();
	}

	private void initialise() throws SQLException {
		// TODO Auto-generated method stub
		if( !hasData ) {
			hasData = true;
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
			if ( !res.next() ) {
				System.out.println("Building the User table with prepopulated values.");
				// building the table
				Statement state2 = con.createStatement();
				state2.execute("Create TABLE user(id integer,"
						+ "username varchar(60)," + "primary key(id));");
				
				// inserting some sample data
				PreparedStatement prep = con.prepareStatement("INSERT INTO user(username) values(?);");
				prep.setString(1, "john wick");
				prep.execute();
				
				PreparedStatement prep2 = con.prepareStatement("INSERT INTO user(username) values(?);");
				prep2.setString(1, "whatever");
				prep2.execute();
				
			}
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
		if(con == null) {
			try {
				getConnection();
				PreparedStatement prep = con.prepareStatement("INSERT INTO user values)");
				prep.setString(2,  user.getName());
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

}

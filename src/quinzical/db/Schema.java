package quinzical.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data base Schema
 * The schema that is used to build quinzical database using sqlite
 * @see quinzical.db.SQLiteDB.java
 * @author Neville
 *
 */
public class Schema {
	
	private static final int USERNAME_CHAR_LIMIT = 20;
	private static final int CATEGORYNAME_CHAR_LIMIT = 50;
	private static final int QUESTION_CHAR_LIMIT = 200;
	
	/**
	 * Create User Table
	 * The table meant to store all user data, where each entry corresponds to one individual
	 * user
	 * @param conn
	 * @throws SQLException
	 */
	public static void createUserTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");		
		if ( !res.next() ) {
			System.out.println("Building the User table");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE user("
					+ "user_id integer,"
					+ "user_name varchar("+ USERNAME_CHAR_LIMIT + ")," 
					+ "highest_score interger,"
					+ "primary key(user_id));");
		}
		
	}
	
	/**
	 * Create Category Table
	 * The table meant to store all category, where question inside a category object is store separately in
	 * the user table. One to many relation ship with question table.
	 * @param conn
	 * @throws SQLException
	 */
	public static  void createCategoryTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='category'");		
		if (!res.next() ) {
			System.out.println("Building the category table");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE category("
					+ "category_id integer,"
					+ "category_name varchar("+ CATEGORYNAME_CHAR_LIMIT +") UNIQUE," 
					+ "primary key(category_id));"
					);
		}
		
	}
	
	/**
	 * Create Question Table
	 * The table meant to store all question, when question hold reference to the category table, 
	 * when a category is deleted, question is removed in a cascade fashion. Many to one relationship
	 * with category table.
	 * @param conn
	 * @throws SQLException
	 */
	public static boolean createQuestionTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='question'");		
		if ( !res.next() ) {
			System.out.println("Building the question table");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE question("
					+ "question_id integer,"
					+ "prompt varchar("+ QUESTION_CHAR_LIMIT +")," 
					+ "answer varchar("+ QUESTION_CHAR_LIMIT +"),"
					+ "answer_prefix varchar("+ QUESTION_CHAR_LIMIT +"),"
					+ "category_id integer,"
					+ "primary key(question_id),"
					+ "FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE);"
					);
			
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Create Session Table
	 * The table meant to store all session data. Each entry represent one question in one session 
	 * hold reference to user id as foreign key. when user is delete
	 * session is removed in a cascade fashion. many to one relationship with user.
	 * many to many relationship with question table.
	 * @param conn
	 * @throws SQLException
	 */
	public static void createSessionTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='session'");		
		if ( !res.next() ) {
			System.out.println("Building the session");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE session("
					+ "session_id integer,"
					+ "user_id integer,"
					+ "score integer,"
					+ "remaining_question integer,"
					+ "isFinished boolean,"
					+ "start_time TIMESTAMP,"
					+ "finish_time TIMESTAMP,"
					+ "primary key(session_id));");
		}
		
	}
	
	/**
	 * Create session_question table
	 * This table is used to facilitate many to many relationship between question and session.
	 * store score that is assigned to the question, and session id, question id as primary key.
	 * and whether the question is attempted. Delete in cascade fashion if session or question is
	 * deleted.
	 * @param conn
	 * @throws SQLException
	 */
	public static void createSessionQuestionsTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='session_questions'");		
		if ( !res.next() ) {
			System.out.println("Building the session_questions table");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE session_questions("
					+ "session_id INT,"
					+ "question_id INT,"
					+ "isAttempted boolean, "
					+ "question_score INT,"
					+ "primary key(session_id, question_id),"
					+ "FOREIGN KEY (session_id) REFERENCES session(session_id) ON DELETE CASCADE,"
					+ "FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE);"
					);
		}
		
	}
	
	/**
	 * Create setting table
	 * Facilitate all data related to settings, for example last user and user settings
	 * @param conn
	 * @throws SQLException
	 */
	public static void createSettingTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='setting'");		
		if ( !res.next() ) {
			System.out.println("Building the setting table");
			Statement state2 = conn.createStatement();
			state2.execute("Create TABLE setting("
					+ "user_id INT,"
					+ "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE SET NULL);"
					);
		}
		
	}
	
	
}

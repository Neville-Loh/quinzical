package quinzical.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Schema {
	
	private static final int USERNAME_CHAR_LIMIT = 20;
	private static final int CATEGORYNAME_CHAR_LIMIT = 50;
	private static final int QUESTION_CHAR_LIMIT = 200;
	
	/**
	 * Create User Table
	 * 
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
					+ "primary key(user_id));");
		}
		
	}
	
	/**
	 * Create Category Table
	 * 
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
	 * @param conn
	 * @throws SQLException
	 */
	public static boolean createQuestionTable(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='question'");		
		if ( !res.next() ) {
			System.out.println("Building the question talbe");
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
	 * 
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
}

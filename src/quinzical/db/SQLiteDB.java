package quinzical.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

import quinzical.exception.QunizicalEntryNotFoundException;
import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;
import quinzical.util.QuestionReader;

/**
 * The implementation of the QuinzicalDB class
 * For detail description @see db.QuinzicalDB.java
 * @author Neville
 *
 */
public class SQLiteDB implements QuinzicalDB{
	
	public static Connection conn = null;
	private static boolean hasData = false;
	
	
	
	/**
	 * Establish connection with the database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void getConnection() throws ClassNotFoundException, SQLException {
		System.out.println("Connecting the database from current directory...");
		Class.forName("org.sqlite.JDBC");
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);  
        conn = DriverManager.getConnection("jdbc:sqlite:SQLiteQuinzical.db",config.toProperties());
		initialise();
	}
	
	/**
	 * initialize database with all table, reads question using question reader.
	 * @throws SQLException
	 */
	private void initialise() throws SQLException {
		if( !hasData ) {
			hasData = true;
			// create all the table
			Schema.createUserTable(conn);
			Schema.createCategoryTable(conn);
			Schema.createSessionTable(conn);
			Schema.createSessionQuestionsTable(conn);
			Schema.createSettingTable(conn);
			boolean init = Schema.createQuestionTable(conn);
			
			
			if (init) {
				// populate question database with by reading from text file
				System.out.println("Initializing questions by reading \"Quinzical.txt\" from current directory");
				QuestionReader rq = new QuestionReader("Quinzical.txt");
				rq.populateCategoriesAndQuestions(this);
			}
			
		}
		
	}
	
	/*
	 * =====================================================================================================
	 * User
	 * Implementation of all end point method of the related
	 * to the class User
	 * 
	 * =====================================================================================================
	 */
	@Override
	public User getUser(int id) {
		PreparedStatement prep = null;
		try {
			String statement = "SELECT * FROM user WHERE user_id = "+ id +";";
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			User result = new User(res.getString(2));
			result.setHighestScore(res.getInt(3));
			result.setUserId(id);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAllUser() {
		List<User> result = new ArrayList<User>();
		String statement = "SELECT * FROM user;";
		PreparedStatement prep = null;
		try {			
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			
			while( res.next() ) {
				User user  = new User(res.getString(2));
				System.out.println(res.getInt(1));
				user.setUserId(res.getInt(1));
				result.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	@Override
	public void updateUser(User user) {
		PreparedStatement prep = null;
		try {
			// saving all data related to the session
			prep = conn.prepareStatement("REPLACE INTO user"
					+ "(user_id, user_name, highest_score) "
					+ "values(?,?,?);");
			prep.setInt(1, user.getUserID());
			prep.setString(2, user.getName());
			prep.setInt(3, user.getHighestScore());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUser(User user) {
		PreparedStatement prep = null;
			try {
				prep = conn.prepareStatement("INSERT INTO user(user_name, highest_score) values(?,?);");
				prep.setString(1, user.getName());
				prep.setInt(2, user.getHighestScore());
				prep.execute();
				
				// Try see if result set return a generatedKeys, set the input object key to id
				try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
					user.setUserId(generatedKeys.getInt(1));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} 
	}

	@Override
	public void deleteUser(int userId) {
		DbUtils.deleteEntryInTable(conn, userId, "user");
	}
	
	/*
	 * =====================================================================================================
	 * Session
	 * Implementation of all end point method of the related
	 * to the class Session 
	 * 
	 * =====================================================================================================
	 */
	@Override
	public Session getUserLastestSession(User user) {
		user.print();
		int userId = user.getUserID();
		String statement = "SELECT * FROM session WHERE user_id = " + userId + " AND  isFinished = false;";
		System.out.println(statement);
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			
			if (!res.next()){
				return null;
			}
			// get all value from result set
			int session_id = res.getInt(1);
			int user_id = res.getInt(2);
			int score = res.getInt(3);
			int remaining_question = res.getInt(4);
			boolean isFinished = res.getBoolean(5);
			Timestamp startTime = res.getTimestamp(6);
			Timestamp FinishTime = res.getTimestamp(7);
			
			// set all value 
			Session session = new Session(user);
			session.setSessionID(session_id);
			session.setWinnings(score); 
			session.setRemainingQuestion(remaining_question);
			session.setIsFinished(false);
			session.setStartTime(startTime);
			session.setFinishTime(FinishTime);
			
			
			// select all question with session id
			statement = "SELECT * FROM session_questions WHERE session_id = "+ session_id +
					" ORDER by question_score DESC;";
			prep = conn.prepareStatement(statement);
			res = prep.executeQuery();
			
			List<Integer> categoryIDs = new ArrayList<Integer>();
			ArrayList<Category> categoryList = new ArrayList<Category>();
			ResultSet resQuestion;
			PreparedStatement prep2;
			while( res.next() ) {
				
				// select the question with question id
				int qid = res.getInt(2);
				boolean isAttempt = res.getBoolean(3);
				int qScore = res.getInt(4);
				
				prep2 = conn.prepareStatement("SELECT * FROM question WHERE question_id = "+ qid +";");
				resQuestion = prep2.executeQuery();
				
				//create new question
				String prompt = resQuestion.getString(2);
				String answer = resQuestion.getString(3);
				String answer_prefix = resQuestion.getString(4);
				int category_id = resQuestion.getInt(5);
				Question question = new Question(prompt, answer, answer_prefix);
				question.setID(qid);
				question.setAttempted(isAttempt);
				question.setScore(qScore);
				
				// if category already exist question to it
				if (categoryIDs.contains(category_id)){
					int index = categoryIDs.indexOf(category_id); 
					categoryList.get(index).add(question);
				} else {
					// else create new category add question to it
					Category newCategory = getEmptyCategory(category_id);
					newCategory.add(question);
					categoryIDs.add(category_id);
					categoryList.add(newCategory);
				}

			}
			
			session.setQuestionSet(categoryList);
			Category hiddenCat = getInternationalQuestionSet(5);
			session.setHiddenCategory(hiddenCat);
			System.out.println(hiddenCat.getTitle());
			return session;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public void addSession(User user, Session session) {
		PreparedStatement prep = null;
		try {
			// saving all data related to the session
			prep = conn.prepareStatement("REPLACE INTO session"
					+ "(session_id,user_id, score, remaining_question, isFinished, start_time, finish_time) "
					+ "values(?,?,?,?,?,?,?);");
			
			int id = session.getSessionID();
			if (id == -1) {
			} else {
				prep.setInt(1, id);
			}
			prep.setInt(2, user.getUserID());
			prep.setInt(3, session.getWinnings());
			prep.setInt(4, session.getRemainingQuestion());
			prep.setBoolean(5, session.isFinished());
			prep.setTimestamp(6, session.getStartTime());
			prep.setTimestamp(7, session.getFinishTime());
			prep.execute();
			
			// assign a id to input object if successful
			try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	int sessionId = generatedKeys.getInt(1);
	            	
	            	if (session.getSessionID() == -1) {
	                session.setSessionID(sessionId);
	            	}
	                
		            // saving each question in database
		            List<Category> cats = session.getCategoryList();
		            for (Category cat : cats) {
		            	for (Question question : cat.getQuestions()) {
		            		prep = conn.prepareStatement("REPLACE INTO session_questions("
		            				+ "session_id, question_id, isAttempted, question_score) values(?,?,?,?);");
		            		prep.setInt(1, sessionId);
		            		prep.setInt(2, question.getID());
		            		prep.setBoolean(3, question.isAttempted());
		            		prep.setInt(4, question.getScore());
		            		prep.execute();
		            	}
		            }
	            }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void deleteSession(int sessionId) {
		DbUtils.deleteEntryInTable(conn, sessionId, "session");
		
	}
	
	/*
	 * =====================================================================================================
	 * Category
	 * Implementation of all end point method of the related
	 * to the class Category
	 * 
	 * =====================================================================================================
	 */

	@Override
	public Category getEmptyCategory(int categoryId) {
		PreparedStatement prep = null;
		try {
			String statement = "SELECT * FROM category WHERE category_id = "+ categoryId +";";
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			Category result = new Category(res.getString(2));
			result.setID(categoryId);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> result = new ArrayList<Category>();
		String statement = "SELECT * FROM category;";
		PreparedStatement prep = null;
		try {			
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			
			while( res.next() ) {
				Category category = new Category(res.getString(2));
				category.setID(res.getInt(1));
				result.add(category);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	@Override
	public List<Category> getRandomQuestionSet(int categoryCount, int questionCount) {
		List<Category> result = new ArrayList<Category>();
		List<String> categoryName = getRandomCategoryName(categoryCount);
		
		for (String catName: categoryName) {
			Category cat = new Category(catName);
			
			// Adding new question 
			PreparedStatement prep = null;
			try {
				String statement = "SELECT question_id, prompt, answer FROM question "
						+ "WHERE category_id = "
						+ DbUtils.getEntryIDInTable(conn, "category" , "category_name" , catName)
						+ " ORDER by RANDOM() LIMIT "+ questionCount +";";
				
				prep = conn.prepareStatement(statement);
				ResultSet res = prep.executeQuery();
				
				/*
				 * the current score is assigned from [100, 500] with 100 increment 
				 */
				int score = 500;
				int increment = 100;
				
				while( res.next() ) {
					Question q = new Question(res.getString(2), res.getString(3));
					q.setID(res.getInt(1));
					
					//setting and updating the score
					q.setScore(score);
					score -= increment;
					
					cat.add(q);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (QunizicalEntryNotFoundException e) {
				e.printStackTrace();
			} 
			
			result.add(cat);
		}
		return result;
		
	}

	
	public Category getInternationalQuestionSet(int questionCount) {
		
		String catName = "International";
		Category cat = new Category(catName);
			
		// Adding new question 
		PreparedStatement prep = null;
		try {
			String statement = "SELECT question_id, prompt, answer FROM question "
					+ "WHERE category_id = "
					+ DbUtils.getEntryIDInTable(conn, "category" , "category_name" , catName)
					+ " ORDER by RANDOM() LIMIT "+ questionCount +";";
				
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
				
			/*
			* the current score is assigned from [100, 500] with 100 increment 
			*/
			int score = 500;
			int increment = 100;
				
			while( res.next() ) {
				Question q = new Question(res.getString(2), res.getString(3));
				q.setID(res.getInt(1));
					
				//setting and updating the score
				q.setScore(score);
				score -= increment;
					
				cat.add(q);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (QunizicalEntryNotFoundException e) {
			e.printStackTrace();
		} 
			
		return cat;
		
	}
	
	
	/**
	 * Get a List of Category name in random order, the number pass in indicate the 
	 * length of the name array to get.
	 * @param numberofCategory to get
	 * @return List of category name
	 */
	public List<String> getRandomCategoryName(int numberofCategory){
		List<String> result = new ArrayList<String>();
		String statement = "SELECT * FROM category ORDER by RANDOM() LIMIT " + numberofCategory + ";";
		PreparedStatement prep = null;
		try {			
			prep = conn.prepareStatement(statement);
			ResultSet res = prep.executeQuery();
			
			while( res.next() ) {
				if (!res.getString(2).equals("International")) {
					result.add(res.getString(2));
				}
			}
			
			if (result.size() < numberofCategory) {
				statement = "SELECT * FROM category ORDER by RANDOM() LIMIT 1;";
				prep = conn.prepareStatement(statement);
				ResultSet replacement = prep.executeQuery();
				result.add(replacement.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
				try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
					category.setID(generatedKeys.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
					
	}

	@Override
	public void deleteCategory(int categoryId) {
		DbUtils.deleteEntryInTable(conn, categoryId, "category");
		
	}
	
	
	/*
	 * =====================================================================================================
	 * Question
	 * Implementation of all end point method of the related
	 * to the class Question
	 * 
	 * =====================================================================================================
	 */
	
	@Override
	public Question getQuestion(int questionId) {
		String statement = "SELECT * FROM question where question_id = "+ questionId +";";
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(statement);
			prep.execute();
			ResultSet rs = prep.executeQuery();
			Question question = new Question(rs.getString(2), rs.getString(3), rs.getString(4));
			question.setID(rs.getInt(1));
			return question;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	@Override
	public Question getRandomQuestionFromCategory(int categoryID) {
		String statement = "SELECT * FROM question where category_id = "+ categoryID
				+ " ORDER by RANDOM() LIMIT 1;";
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(statement);
			prep.execute();
			ResultSet rs = prep.executeQuery();
			Question question = new Question(rs.getString(2), rs.getString(3), rs.getString(4));
			question.setID(rs.getInt(1));
			
			return question;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public void addQuestion(Question question, int categoryId) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement("INSERT INTO question(prompt,answer, answer_prefix, category_id) VALUES(?,?,?,?);");
			prep.setString(1, question.toString());
			prep.setString(2, question.getAnswer());
			prep.setString(3, question.getAnswerPrefix());
			prep.setInt(4, categoryId);
			prep.execute();
			
			try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
				question.setID(generatedKeys.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void deleteQuestion(int questionId) {
		DbUtils.deleteEntryInTable(conn, questionId, "question");
		
	}



}

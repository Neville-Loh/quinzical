package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.sqlite.core.DB;

import quinzical.db.DbUtils;
import quinzical.db.QuinzicalDB;
import quinzical.db.SQLiteDB;

import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.Session;
import quinzical.model.User;
import quinzical.util.QuestionReader;

public class testSQLiteDB {

	private SQLiteDB db;
	
	
	public testSQLiteDB() {
		//
		
		System.out.println("DBtest start");
		long startTime = System.currentTimeMillis();
		db = new SQLiteDB();

		try {
			
			db.getConnection();
			
		
			testSessionSaving();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

			
	}
	/**
	 * Test the session saving and loading functionality
	 * 
	 */
	private void testSessionSaving() {
		User user = new User("Neville");
		try {
			db.addUser(user);
		} catch (Exception e) {
			
		}
		
		Session session = new Session(user);
		
		List<Category> cats = db.getRandomQuestionSet(5, 5);
		session.setQuestionSet(cats);
		printCategorySet(cats);
		
//		Question q = session.getQuestionById(85);
//		q.setAttempted(true);
		//db.addSession(user, session);
		
		Question q = db.getQuestion(71);
		
		printQuestion(q);
		
	}
	public void testOnDelectCascade() {
		
	}
	
	/**
	 * Test function of db to generate a random question set.
	 */
	public void testgetRandomQuestionSet(){
	try {
			
		List<String> names = new ArrayList<String>();
		names.add("Animal");
		names.add("Sport");
		names.add("Country");
		
		
		List<Category> cats = db.getRandomQuestionSet(5,5);
		printCategorySet(cats);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	/**
	 * populate data base with some random session data
	 * use for testing purpose
	 * @param db
	 */
	public void populateSession(QuinzicalDB db) {
		User user = new User("Tom");
		user.setUserId(1);
		Session session = new Session(user);
		db.addSession(user, session);
	}
	

	/**
	 * populate data base with some random user
	 * use for testing purpose
	 * @param db
	 */
	public  void populateUser(QuinzicalDB db) {
		User user;
		user = new User("Tom");
		db.addUser(user);
		
		user = new User("Amy");
		db.addUser(user);
		
		user = new User("Neville");
		db.addUser(user);
		
	}
	
	/**
	 * populate Category with some random value
	 * @param db
	 */
	public void populateCategory(SQLiteDB db) {
		db.addCategory(new Category("Animal"));
		db.addCategory(new Category("Sport"));
		db.addCategory(new Category("Country"));
	}
	
	/**
	 * populate the question data with some testing quetsion
	 * @param db
	 */
	public void populateQuetsion(SQLiteDB db) {
		Question question1 = new Question("This is the capital of New Zealand", "cloud");
		db.addQuestion(question1, 1);
		Question question2 = new Question("What is somthing that is blue?", "sky");
		db.addQuestion(question2, 2);
		Question question3 = new Question("What is somthing that is black?", "my future");
		db.addQuestion(question3, 1);
		Question question4 = new Question("What is somthing that move", "pigs");
		db.addQuestion(question4, 1);
		
		int count = 50;
		while (count > 0) {
			Question q = new Question("This is question number " + count , "Ans " + count);
			count--;
			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			db.addQuestion(q, randomNum);
		}
		
	}
	
	/**
	 * Utility function to print all question in a question set 
	 * @param cats
	 */
	public static void printCategorySet(List<Category> cats) {
		System.out.printf("Category length : %d%n", cats.size());
		
		for (Category cat : cats) {
			System.out.println(cat.getTitle());
			for (Question question : cat.getQuestions()) {
				printQuestion(question);
			}
		}
	}
	
	/**
	 * Utility function to print all question in a question set 
	 * @param question
	 */
	public static void printQuestion(Question question) {
		System.out.printf("Id: %d, attempted = %s, prompt: %s , ans: %s%n", 
				question.getID(), ""+question.isAttempted(), question.toString(), question.getAnswer());
	}
	
	
}

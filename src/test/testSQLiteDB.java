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
import quinzical.db.Table;
import quinzical.db.Table.USER;
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
			
//			QuestionReader rq = new QuestionReader("Quinzical.txt");
//			rq.populateCategoriesAndQuestions(db);
			
			
			
			
			
//			populateUser(db);
//			populateCategory(db);
//			populateQuetsion(db);
//			populateSession(db);
//			db.deleteCategory(3);
//			
//			System.out.println("all is good");
//			long stopTime = System.currentTimeMillis();
//			System.out.println((stopTime - startTime)/1000.0);
			
			testgetRandomQuestionSet();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		ResultSet res;
//		db.addUser(new User());
//		
		

//		try {
//			res = db.displayUsers();
//			while(res.next()) {
//				System.out.println(res.getString("username"));
//			}
//			
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		
	}
	public void testOnDelectCascade() {
		
	}
	private void populateUser(QuinzicalDB db) {
		User user;
		user = new User("Tom");
		db.addUser(user);
		
		user = new User("Amy");
		db.addUser(user);
		
		user = new User("Neville");
		db.addUser(user);
		
	}
	
	private void testgetRandomQuestionSet(){
		
	try {
			
		List<String> names = new ArrayList<String>();
		names.add("Animal");
		names.add("Sport");
		names.add("Country");
		
		
		List<Category> cats = db.getRandomQuestionSet(5);
		printCategorySet(cats);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	public void testSessionSaveAndLoad(QuinzicalDB db) {
		User user = new User("Ton");
		
	}
	
	
	
	private void populateSession(QuinzicalDB db) {
		User user = new User("Tom");
		user.setUserId(1);
		Session session = new Session(user);
		db.addSession(user, session);
	}
	
	private void populateSessionsQuestion(QuinzicalDB db) {
	}
	
	private void populateCategory(SQLiteDB db) {
		db.addCategory(new Category("Animal"));
		db.addCategory(new Category("Sport"));
		db.addCategory(new Category("Country"));
	}
	
	private void populateQuetsion(SQLiteDB db) {
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
	
	public static void printCategorySet(List<Category> cats) {
		System.out.printf("Category length : %d%n", cats.size());
		
		for (Category cat : cats) {
			System.out.println(cat.getTitle());
			for (Question question : cat.getQuestions()) {
				System.out.printf("prompt: %s , ans: %s id = %d \n", 
						question.toString(), question.getAnswer(), question.getID());
			}
		}
	}
	
	
}

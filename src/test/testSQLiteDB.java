package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import quinzical.db.QuinzicalDB;
import quinzical.db.SQLiteDB;
import quinzical.model.Question;
import quinzical.model.User;

public class testSQLiteDB {

	
	public testSQLiteDB() {
		//
		
		SQLiteDB db = new SQLiteDB();

		try {
			db.getConnection();
			db.deleteCategory(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		ResultSet res;
//		db.addUser(new User());
//		
//		
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
	public void populateUser(QuinzicalDB db) {

	}
	
	
	
	public void populateSession() {
		
	}
	
	private void populateSessionsQuestion() {
		
	}
	
	private void populateCategory(SQLiteDB db) {
		db.addCategory("Animal");
		db.addCategory("Sport");
		db.addCategory("Country");
	}
	
	private void populateQuetsion(SQLiteDB db) {
		Question question1 = new Question("What is somthing that is white?", "cloud", 0);
		db.addQuestion(question1, 1);
		Question question2 = new Question("What is somthing that is blue?", "sky", 0);
		db.addQuestion(question2, 2);
		Question question3 = new Question("What is somthing that is black?", "my future", 0);
		db.addQuestion(question3, 1);
		Question question4 = new Question("What is somthing that move", "pigs", 0);
		db.addQuestion(question4, 1);
	}
}

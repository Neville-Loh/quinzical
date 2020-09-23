package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import quinzical.db.SQLiteDB;

public class testSQLiteDB {
	
	public testSQLiteDB() {
		//
		
		SQLiteDB db = new SQLiteDB();
		ResultSet res;
		
		try {
			res = db.displayUsers();
			while(res.next()) {
				System.out.println(res.getString("username"));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}

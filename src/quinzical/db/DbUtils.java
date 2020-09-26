package quinzical.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import quinzical.exception.QunizicalEntryNotFoundException;
import quinzical.model.Question;

public class DbUtils {
	public static void deleteEntryInTable(Connection conn, int entryId, String tableName) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement("DELETE FROM "
					+ tableName
					+ " WHERE " + tableName + "_id = " + entryId + ";");
			prep.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	public static int getEntryIDInTable(Connection conn, String tableName, String colName , 
			String colValue) throws QunizicalEntryNotFoundException {
		PreparedStatement prep = null;
		try {
			String statement = "SELECT "+ tableName + "_id FROM "
					+ tableName
					+ " WHERE " + colName + " = '" + colValue + "';";
			
			System.out.println(statement);
			prep = conn.prepareStatement(statement);
			ResultSet r = prep.executeQuery();
			
			

			return r.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new QunizicalEntryNotFoundException("Operation not sucessful");
	}
	
//	public static Question resultSetToQuestion(ResultSet res) throws SQLException {
//		while( res.next() ) {
//			System.out.println("" + res.getInt(1) + " "+  res.getString(2) + " "+ res.getString(3));
//			Question q = new Question(res.getString(2), res.getString(3));
//			q.setID(res.getInt(1));
//		}
//		return q;
//	}
}

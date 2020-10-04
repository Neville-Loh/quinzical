package quinzical.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import quinzical.exception.QunizicalEntryNotFoundException;
import quinzical.model.Question;
/**
 * Class for database helper functions.
 * @author Neville
 */
public class DbUtils {
	
	/**
	 * Delete an entry from the give tableName, irreversible action.
	 * All entry related to the entry will be deleted in a cascade faction.
	 * @param conn connection of the database
	 * @param entryId the id asigned to the entry
	 * @param tableName the table Name
	 */
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
	
	
	/**
	 * get an entry ID given the table name, column  name, where the column value to match it
	 * Will only return the first entry if there are multiple matches
	 * catch exception if no matches
	 * 
	 * @param conn connection of the database
	 * @param tableName the Name of the table
	 * @param colName name of the column
	 * @param colValue name of the column
	 * @return the ID of the entry
	 * @throws QunizicalEntryNotFoundException
	 */
	public static int getEntryIDInTable(Connection conn, String tableName, String colName , 
			String colValue) throws QunizicalEntryNotFoundException {
		PreparedStatement prep = null;
		try {
			String statement = "SELECT "+ tableName + "_id FROM "
					+ tableName
					+ " WHERE " + colName + " = '" + colValue + "';";
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

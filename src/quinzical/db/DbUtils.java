package quinzical.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import quinzical.exception.QunizicalEntryNotFoundException;

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
			prep = conn.prepareStatement("SELECT "+ tableName + "_id FROM "
					+ tableName
					+ " WHERE " + colName + " = '" + colValue + "';");
			ResultSet r = prep.executeQuery();
			
			

			return r.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new QunizicalEntryNotFoundException("Operation not sucessful");
	}
}

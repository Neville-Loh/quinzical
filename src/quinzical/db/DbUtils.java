package quinzical.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtils {
	public static void deleteEntryInTable(Connection conn, int entryId, String tableName) {
		PreparedStatement prep = null;
		
		System.out.println(conn);
		try {
			prep = conn.prepareStatement("DELETE FROM "
					+ tableName
					+ " WHERE " + tableName + "_id = " + entryId + ";");
			prep.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}

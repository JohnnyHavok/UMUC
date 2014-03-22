package DBTools;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTools {
  protected static boolean checkDB(Connection conn) throws SQLException {
    try {
      conn.createStatement().execute("SELECT * FROM ACCOUNT_T");
    } catch (SQLException e) {
      if(e.getSQLState().equals("42X05")) // Table does not exist
        return false;
      else
        throw e; // Unhandled exception
    }

    return true;
  }
}

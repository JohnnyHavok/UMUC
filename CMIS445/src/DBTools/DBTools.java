package DBTools;

import java.sql.*;

public class DBTools {
  protected static int addAccount(Connection conn,
                                  String LastName,
                                  String FirstName,
                                  String PIN,
                                  double checkingBal,
                                  double savingsBal) throws SQLException {

    int accountID = 0;

    try {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO ACCOUNT_T" +
          "(LastName, FirstName, PIN, CheckingBalance, SavingsBalance) " +
          "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, LastName);
      ps.setString(2, FirstName);
      ps.setString(3, PIN);
      ps.setDouble(4, checkingBal);
      ps.setDouble(5, savingsBal);
      ps.execute();

      ResultSet rs = ps.getGeneratedKeys();

      if(rs.next()) {
        accountID = rs.getInt(1);
        addTransaction(conn, accountID, "New Account Setup", "Checking", checkingBal);
        addTransaction(conn, accountID, "New Account Setup", "Savings", savingsBal);
      }
    } catch (SQLException e) {
      throw e; // Unhandled SQL Exception
    }

    return accountID;
  }

  protected static boolean addTransaction( Connection conn, int accountID, String desc,
                                           String accType, double amount ) throws SQLException {
    try {
      Statement s = conn.createStatement();

      String transaction = new StringBuilder().append("INSERT INTO TRANSACTION_T")
          .append(" VALUES(")
          .append(accountID).append(",'")
          .append(desc).append("','")
          .append(accType).append("',")
          .append(amount).append(")")
          .toString();

      s.execute(transaction);

    } catch (SQLException e) {
      throw e; // Unhandled SQL Exception
    }
    // TODO: Create meaning full test of transaction correctness
    return true;
  }

  protected static boolean addUser( Connection conn, String userID, String pin )
                                     throws SQLException {
    try {
      conn.createStatement().execute("INSERT INTO USERS_T VALUES('"+userID+"','"+pin+"')");
    } catch (SQLException e) {
      throw e; // Unhandled SQL Exception
    }

    // TODO: Create meaningful test of correctness
    return true;
  }


  protected static boolean checkDB(Connection conn) throws SQLException {
    try {
      conn.createStatement().execute("SELECT * FROM USERS_T");
    } catch (SQLException e) {
      if(e.getSQLState().equals("42X05")) // Table does not exist
        return false;
      else
        throw e; // Unhandled exception
    }

    return true;
  }
}

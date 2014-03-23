package DBTools;

import java.sql.*;

public class DBTools {
  public static Connection dbConnect() throws SQLException {
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String dbName = "BankDB";
    String URL    = "jdbc:derby:" + dbName + ";create=false";

    Connection conn = null;

    // -- LOAD JDBC DRIVER --
    try {
      Class.forName(driver);
      System.out.println(driver + " loaded successfully!");
    } catch (ClassNotFoundException e) {
      System.err.println("Derby Embedded Driver Not Found!");
      System.err.println(e.getMessage());
    }

    // -- CONNECT TO DATABASE --
    try {
      conn = DriverManager.getConnection(URL);

      System.out.println("CONNECTED TO DATABASE "+ dbName);

      // -- CHECK FOR TABLES, BUILD TABLES IF NOT PRESENT --
      try {
        if(!DBTools.checkDB(conn))
          throw new SQLException("Database not initialized, run Bootstrap process!");
      } catch (SQLException e) {
        System.err.println("Unhandled Exception from checkDB:");
        System.err.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.err.println("You must run BankServer.jar from the root directory of the project");
      System.err.println("Either use the ANT build file or use java -jar bin/BankServer.jar");
      System.err.println(e.getMessage());
    }

    return conn;
  }

  public static int addAccount(Connection conn,
                                  String LastName,
                                  String FirstName,
                                  String SSN,
                                  String PIN,
                                  double checkingBal,
                                  double savingsBal) throws SQLException {

    int accountID = 0;

    try {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO ACCOUNT_T" +
          "(LastName, FirstName, SSN, PIN, Checking, Savings) " +
          "values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, LastName);
      ps.setString(2, FirstName);
      ps.setString(3, SSN);
      ps.setString(4, PIN);
      ps.setDouble(5, checkingBal);
      ps.setDouble(6, savingsBal);
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

  public static int addAccount(Connection conn, BankService.Customer customer)
                                  throws SQLException {
    int accountID = 0;

    try {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO ACCOUNT_T" +
          "(LastName, FirstName, SSN, PIN, Checking, Savings) " +
          "values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, customer.lastName);
      ps.setString(2, customer.firstName);
      ps.setString(3, customer.SSN);
      ps.setString(4, customer.pin);
      ps.setDouble(5, customer.checkingBalance);
      ps.setDouble(6, customer.SavingsBalance);
      ps.execute();

      ResultSet rs = ps.getGeneratedKeys();

      if(rs.next()) {
        accountID = rs.getInt(1);
        addTransaction(conn, accountID, "New Account Setup", "Checking", customer.checkingBalance);
        addTransaction(conn, accountID, "New Account Setup", "Savings", customer.SavingsBalance);
      }
    } catch (SQLException e) {
      throw e; // Unhandled SQL Exception
    }

    return accountID;
  }

  public static BankService.Customer getAccount(Connection conn, int accountID) throws SQLException {
    BankService.Customer c = new BankService.Customer();

    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ACCOUNT_T " +
                                           "WHERE AccountID = " + accountID);
      if(rs.next()) {
        c.accountID = rs.getInt("AccountID");
        c.lastName = rs.getString("LastName");
        c.firstName = rs.getString("FirstName");
        c.SSN = rs.getString("SSN");
        c.pin = rs.getString("PIN");
        c.checkingBalance = rs.getDouble("Checking");
        c.SavingsBalance = rs.getDouble("Savings");
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw e;
    }

    return c;
  }

  public static int getAccountID(Connection conn, String SSN) throws SQLException {
    int id;

    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT AccountID FROM ACCOUNT_T " +
                                            "WHERE SSN = '" + SSN + "'");

      if(rs.next()) {
        return rs.getInt("AccountID");
      } else {
        throw new SQLException("SSN of " + SSN + " does not have an account!");
      }
    } catch (SQLException e) {
      throw e;
    }
  }

  private static boolean addTransaction( Connection conn, int accountID, String desc,
                                           String accType, double amount ) throws SQLException {
    try {
      Statement s = conn.createStatement();

      String transaction = new StringBuilder().append("INSERT INTO TRANSACTION_T(AccountID, Description, AccountType, Amount)")
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

  public static double deposit( Connection conn, int accountID, String accType, double amount )
                                throws SQLException {
    try {
      Statement s = conn.createStatement();

      String q = new StringBuilder().append("UPDATE ACCOUNT_T ")
          .append("SET ").append(accType).append(" = ").append(accType)
          .append(" + ").append(amount).append("WHERE AccountID = ").append(accountID)
          .toString();

      s.executeUpdate(q);

      DBTools.addTransaction(conn, accountID, "Deposit Made", accType, amount);

      if(accType.equalsIgnoreCase("checking"))
        return DBTools.getCheckingBalance(conn, accountID);

      if(accType.equalsIgnoreCase("savings"))
        return DBTools.getSavingsBalance(conn, accountID);

      return 0;


    } catch (SQLException e) {
      throw e;
    }
  }

  public static double withdraw( Connection conn, int accountID, String accType, double amount )
      throws SQLException {
    try {
      Statement s = conn.createStatement();

      String q = new StringBuilder().append("UPDATE ACCOUNT_T ")
          .append("SET ").append(accType).append(" = ").append(accType)
          .append(" - ").append(amount).append("WHERE AccountID = ").append(accountID)
          .toString();

      s.executeUpdate(q);

      DBTools.addTransaction(conn, accountID, "Withdraw Made", accType, amount);

      if(accType.equalsIgnoreCase("checking"))
        return DBTools.getCheckingBalance(conn, accountID);

      if(accType.equalsIgnoreCase("savings"))
        return DBTools.getSavingsBalance(conn, accountID);

      return 0;


    } catch (SQLException e) {
      throw e;
    }
  }

  public static double transfer( Connection conn, int fromAccountID, String fromAccountType,
                                 int toAccountID, String toAccountType, double amount) throws SQLException {
    try {
      Statement s = conn.createStatement();
      String withdraw = new StringBuilder().append("UPDATE ACCOUNT_T ")
          .append("SET ").append(fromAccountType).append(" = ").append(fromAccountType)
          .append(" - ").append(amount).append("WHERE AccountID = ").append(fromAccountID)
          .toString();
      s.execute(withdraw);

      DBTools.addTransaction(conn, fromAccountID, "Transfer To: " + toAccountID, fromAccountType, amount);

      String deposit = new StringBuilder().append("UPDATE ACCOUNT_T ")
          .append("SET ").append(toAccountType).append(" = ").append(toAccountType)
          .append(" + ").append(amount).append("WHERE AccountID = ").append(toAccountID)
          .toString();
      s.execute(deposit);

      DBTools.addTransaction(conn, toAccountID, "Transfer From: " + fromAccountID, fromAccountType, amount);

      if(fromAccountType.equalsIgnoreCase("checking"))
        return DBTools.getCheckingBalance(conn, fromAccountID);

      if(fromAccountType.equalsIgnoreCase("savings"))
        return DBTools.getSavingsBalance(conn, fromAccountID);

      return 0;
    } catch (SQLException e) {
      throw e;
    }
  }

  public static double cashCheck( Connection conn, int accountID, int checkNum, double amount )
      throws SQLException {
    try {
      Statement s = conn.createStatement();

      String q = new StringBuilder().append("UPDATE ACCOUNT_T ")
          .append("SET ").append("Checking").append(" = ").append("Checking")
          .append(" - ").append(amount).append("WHERE AccountID = ").append(accountID)
          .toString();

      s.executeUpdate(q);

      DBTools.addTransaction(conn, accountID, "Check Cashed: " + checkNum, "Checking", amount);

      return DBTools.getCheckingBalance(conn, accountID);
    } catch (SQLException e) {
      throw e;
    }
  }

  public static double getCheckingBalance( Connection conn, int accountID) throws SQLException {
    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT Checking FROM Account_T " +
          "WHERE AccountID = " + accountID);

      if(rs.next())
        return rs.getDouble("Checking");

      else return 0;

    } catch (SQLException e) {
      throw e;
    }
  }

  public static double getSavingsBalance( Connection conn, int accountID) throws SQLException {
    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT Savings FROM Account_T " +
          "WHERE AccountID = " + accountID);

      if(rs.next())
        return rs.getDouble("Savings");

      else return 0;

    } catch (SQLException e) {
      throw e;
    }
  }

  public static boolean addUser( Connection conn, String userID, String pin )
                                     throws SQLException {
    try {
      conn.createStatement().execute("INSERT INTO USERS_T VALUES('" + userID + "','" + pin + "')");
    } catch (SQLException e) {
      throw e; // Unhandled SQL Exception
    }

    // TODO: Create meaningful test of correctness
    return true;
  }

  public static boolean verifyUser( Connection conn, String userID, String pin )
                                        throws SQLException {
    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT UserID FROM USERS_T " +
                              "WHERE UserID = '" + userID + "' AND " +
                                    "UserPin = '" + pin + "'");

      if(rs.next())
        return true;

      return false;

    } catch (SQLException e) {
      throw e;
    }
  }

  public static boolean checkPIN( Connection conn, int accountID, String pin )
      throws SQLException {
    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT AccountID FROM Account_T " +
          "WHERE AccountID = " + accountID + " AND " +
          "PIN = '" + pin + "'");

      if(rs.next())
        return true;

      return false;

    } catch (SQLException e) {
      throw e;
    }
  }

  public static boolean checkAccount( Connection conn, int accountID ) throws SQLException {
    try {
      ResultSet rs = conn.createStatement().executeQuery("SELECT AccountID FROM Account_T " +
          "WHERE AccountID = " +  accountID);

      if(rs.next())
        return true;

      return false;
    } catch (SQLException e) {
      throw e;
    }
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

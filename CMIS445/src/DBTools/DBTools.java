package DBTools;

import java.sql.*;

public class DBTools {

  public static void main(String[] args) {
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String dbName = "BankDB";
    String URL    = "jdbc:derby:" + dbName + ";create=true";

    Connection conn = null;
    Statement s;
    PreparedStatement ps;
    ResultSet r;

    String createAccountTable =
        new StringBuilder().append("CREATE TABLE ACCOUNT_T ")
            .append("( AccountID INT NOT NULL GENERATED ALWAYS AS IDENTITY ")
            .append("           ( START WITH 50000,  INCREMENT BY 7 ) ")
            .append("           CONSTRAINT Account_PK PRIMARY KEY, ")
            .append("  LastName VARCHAR(32) NOT NULL, ")
            .append("  FirstName VARCHAR(32) NOT NULL, ")
            .append("  PIN SMALLINT NOT NULL, ")
            .append("  CheckingBalance DECIMAL(12, 2) NOT NULL, ")
            .append("  SavingsBalance DECIMAL(12, 2) NOT NULL ) ")
            .toString();

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
      s = conn.createStatement();

      System.out.println("CONNECTED TO DATABASE "+ dbName);

    // -- CHECK FOR TABLES, BUILD TABLES IF NOT PRESENT --
      try {
        if(!checkDB(conn)) {
          System.out.println("CREATING FIRST TIME DATABASE TABLES");
          System.out.println("CREATING ACCOUNT_T");
          s.execute(createAccountTable);
        } else {
          System.out.println("DATABASE EXIST, RETAINING DB FOR USE");
        }
      } catch (SQLException e) {
        System.err.println("Unhandled Exception from checkDB:");
        System.err.println(e.getMessage());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static boolean checkDB(Connection conn) throws SQLException {
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

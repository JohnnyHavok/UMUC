package DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Bootstrap {

  public static void main(String[] args) {
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String dbName = "BankDB";
    String URL    = "jdbc:derby:" + dbName + ";create=true";

    Connection conn;

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
        if(!DBTools.checkDB(conn)) {
          bootstrap(conn);
        } else {
          System.out.println("DATABASE EXIST, RETAINING DB FOR USE");
        }
      } catch (SQLException e) {
        System.err.println("Unhandled Exception from checkDB:");
        System.err.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.err.println("Unhandled Exception while bootstrapping");
      System.err.println(e.getMessage());
    }
  }

  private static void bootstrap(Connection conn) throws SQLException {
    Statement s = conn.createStatement();

    // -- Table Creation SQL Statements --
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

    String createTransactionTable =
        new StringBuilder().append("CREATE TABLE TRANSACTION_T ")
            .append("( AccountID INT NOT NULL ")
            .append("       CONSTRAINT AccountID_FK REFERENCES Account_T ")
            .append("       ON DELETE NO ACTION ON UPDATE RESTRICT, ")
            .append(" Description VARCHAR(32) NOT NULL, ")
            .append(" AccountType VARCHAR(8) NOT NULL, ")
            .append(" Amount DECIMAL(12, 2) NOT NULL ) ")
            .toString();



    try {
      System.out.println("CREATING FIRST TIME DATABASE TABLES");
      System.out.println("CREATING ACCOUNT_T");
      s.execute(createAccountTable);
      System.out.println("CREATING TRANACTION_T");
      s.execute(createTransactionTable);

      DBTools.addAccount(conn, "Smith", "Justin", 1234, 5432.10, 9876.54);
    } catch (SQLException e) {
      throw e;
    }
  }
}

package BankServer;

import DBTools.DBTools;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServant {
  private Connection dbConnection;

  public BankServant(Connection c) {
    dbConnection = c;
  }

  @Override
  public boolean login(String userID, String PIN) {
    try {
      return DBTools.verifyUser(dbConnection, userID, PIN);
    } catch (SQLException e) {
      System.err.println("SQL Error Reached BankServant.login");
      System.err.println(e.getMessage());
    }
    return false;
  }
}

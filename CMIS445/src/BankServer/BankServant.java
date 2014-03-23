package BankServer;

import BankService.Customer;
import BankService.TellerServicePOA;
import DBTools.DBTools;
import org.omg.CORBA.ORB;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServant extends TellerServicePOA {
  private Connection dbConnection;
  private ORB broker;

  public BankServant(ORB broker, Connection c) {
    this.broker = broker;
    this.dbConnection = c;
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

  @Override
  public double deposit(int accountID, String accountType, int amount) {
    return 0;
  }

  @Override
  public double withdraw(int accountID, String accountType, int amount) {
    return 0;
  }

  @Override
  public double cashCheck(int accountID, int checkNumber, int amount) {
    return 0;
  }

  @Override
  public double transfer(int accountID, String accountType, int toAccountID, int amount) {
    return 0;
  }

  @Override
  public int createAccount(Customer newCustomer) {
    return 0;
  }

  @Override
  public void shtudown() {
    broker.shutdown(true);
  }
}

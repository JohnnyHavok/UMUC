package BankServer;

import BankService.Customer;
import BankService.TellerServicePOA;
import BankService.TellerServicePackage.AccountAlreadyExist;
import BankService.TellerServicePackage.AccountNotFound;
import BankService.TellerServicePackage.InsufficientFunds;
import BankService.TellerServicePackage.ShutdownMessage;
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
    System.out.println("BankServant responding to login()");
    try {
      return DBTools.verifyUser(dbConnection, userID, PIN);
    } catch (SQLException e) {
      System.err.println("SQL Error Reached BankServant.login");
      System.err.println(e.getMessage());
    }
    return false;
  }

  @Override
  public double deposit(int accountID, String accountType, double amount) {
    System.out.println("BankServant responding to deposit()");
    try {
      return DBTools.deposit(dbConnection, accountID, accountType, amount);
    } catch (SQLException e) {
      System.err.println("SQL Error Reached BankServant.deposit");
      System.err.println(e.getMessage());
    }

    return 0;
  }

  @Override
  public double withdraw(int accountID, String accountType, double amount) throws InsufficientFunds {
    return 0;
  }

  @Override
  public double cashCheck(int accountID, int checkNumber, double amount) throws InsufficientFunds {
    return 0;
  }

  @Override
  public double transfer(int accountID, String accountType, int toAccountID, double amount) throws InsufficientFunds {
    return 0;
  }

  @Override
  public double getCheckingBalance(int accountID) throws AccountNotFound {
    return 0;
  }

  @Override
  public double getSavingsBalance(int accountID) throws AccountNotFound {
    return 0;
  }

  @Override
  public int createAccount(Customer newCustomer) throws AccountAlreadyExist {
    return 0;
  }

  @Override
  public int getAccountID(String SSN) throws AccountNotFound {
    return 0;
  }

  @Override
  public Customer getAccount(String accountID) throws AccountNotFound {
    return null;
  }

  @Override
  public void shutdown() throws ShutdownMessage {
    throw new ShutdownMessage("Shutdown requested by BankServant");
  }
}

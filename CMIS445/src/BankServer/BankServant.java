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
    try {
      if(accountType.equalsIgnoreCase("checking")) {
        if(this.getCheckingBalance(accountID) > amount)
          return DBTools.withdraw(dbConnection, accountID, accountType, amount);
        else
          throw new InsufficientFunds("Checking Account has Insufficient Funds");
      } else if(accountType.equalsIgnoreCase("savings")) {
        if(this.getSavingsBalance(accountID) > amount)
          return DBTools.withdraw(dbConnection, accountID, accountType, amount);
        else
          throw new InsufficientFunds("Savings Account has Insufficient Funds");
      }
    } catch (AccountNotFound accountNotFound) {
      // Stuck in Exception Hell, puke message for now
      // TODO: Fix exception handling for multi-part calls.
      accountNotFound.getMessage();
    } catch (SQLException e) {
      System.err.println("SQL Error Reached BankServant.withdraw");
      System.err.println(e.getMessage());
    }
    return 0;
  }

  @Override
  public double cashCheck(int accountID, int checkNumber, double amount) throws InsufficientFunds {
    try {
      if(this.getCheckingBalance(accountID) > amount)
        return DBTools.cashCheck(dbConnection, accountID, checkNumber, amount);
      else
        throw new InsufficientFunds("Checking Account Has Insufficient Funds for Check");
    } catch (SQLException e) {
      System.err.println("SQL Error Reached BankServant.cashCheck");
      System.err.println(e.getMessage());
    } catch (AccountNotFound accountNotFound) {
      // Stuck in Exception Hell, puke message for now
      // TODO: Fix exception handling for multi-part calls.
      accountNotFound.getMessage();    }

    return 0;
  }

  @Override
  public double transfer(int accountID, String accountType, int toAccountID, double amount) throws InsufficientFunds {
    return 0;
  }

  @Override
  public double getCheckingBalance(int accountID) throws AccountNotFound {
    try {
      System.out.println("BankServant responding to getCheckingBalance()");
      return DBTools.getCheckingBalance(dbConnection, accountID);
    } catch (SQLException e) {
      System.err.println("Caught SQLException in getCheckingBalance()");
      System.err.println(e.getMessage());
      throw new AccountNotFound("AccountID was not found");
    }
  }

  @Override
  public double getSavingsBalance(int accountID) throws AccountNotFound {
    try {
      System.out.println("BankServant responding to getSavingsBalance()");
      return DBTools.getSavingsBalance(dbConnection, accountID);
    } catch (SQLException e) {
      System.err.println("Caught SQLException in getSavingsBalance()");
      System.err.println(e.getMessage());
      throw new AccountNotFound("AccountID was not found");
    }
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

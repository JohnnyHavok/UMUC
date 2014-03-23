package BankServer;

import BankService.Customer;
import BankService.TellerServicePOA;
import BankService.TellerServicePackage.AccountAlreadyExist;
import BankService.TellerServicePackage.AccountNotFound;
import BankService.TellerServicePackage.InsufficientFunds;
import DBTools.DBTools;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServant extends TellerServicePOA {
  private Connection dbConnection;

  public BankServant(Connection c) {
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
    System.out.println("BankServant responding to withdraw()");
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
    System.out.println("BankServant responding to cashCheck()");
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
      accountNotFound.getMessage();
    }

    return 0;
  }

  @Override
  public double transfer(int accountID, String accountType, int toAccountID, String toAccountType,
                         double amount) throws InsufficientFunds {
    System.out.println("BankServant responding to transfer()");
    try {
      if(accountType.equalsIgnoreCase("checking")) {
        if(this.getCheckingBalance(accountID) > amount)
          return DBTools.transfer(dbConnection, accountID, accountType, toAccountID, toAccountType, amount);
        else
          throw new InsufficientFunds("Checking Account has Insufficient Funds");
      } else if(accountType.equalsIgnoreCase("savings")) {
        if(this.getSavingsBalance(accountID) > amount)
          return DBTools.transfer(dbConnection, accountID, accountType, toAccountID, toAccountType, amount);
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
  public double getCheckingBalance(int accountID) throws AccountNotFound {
    System.out.println("BankServant responding to getCheckingBalance()");
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
    System.out.println("BankServant responding to getSavingsBalance()");
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
    System.out.println("BankServant responding to createAccount()");
    try {
      return DBTools.addAccount(dbConnection, newCustomer);
    } catch (SQLException e) {
      throw new AccountAlreadyExist("Account could not be created, could possibly exist already");
    }
  }

  @Override
  public int getAccountID(String SSN) throws AccountNotFound {
    System.out.println("BankServant responding to getAccountID()");
    try {
      return DBTools.getAccountID(dbConnection, SSN);
    } catch (SQLException e) {
      throw new AccountNotFound("Account not associated with this SSN");
    }
  }

  @Override
  public Customer getAccount(int accountID) throws AccountNotFound {
    System.out.println("BankServant responding to getAccount()");
    try {
      return DBTools.getAccount(dbConnection, accountID);
    } catch (SQLException e) {
      throw new AccountNotFound("AccountID Not Found");
    }
  }
}

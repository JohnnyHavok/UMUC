package BankServer;

import BankService.*;
import DBTools.DBTools;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServant implements AuthTeller, TellerService {
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
  public boolean _is_a(String repositoryIdentifier) {
    return false;
  }

  @Override
  public boolean _is_equivalent(org.omg.CORBA.Object other) {
    return false;
  }

  @Override
  public boolean _non_existent() {
    return false;
  }

  @Override
  public int _hash(int maximum) {
    return 0;
  }

  @Override
  public Object _duplicate() {
    return null;
  }

  @Override
  public void _release() {

  }

  @Override
  public Object _get_interface_def() {
    return null;
  }

  @Override
  public Request _request(String operation) {
    return null;
  }

  @Override
  public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
    return null;
  }

  @Override
  public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exclist, ContextList ctxlist) {
    return null;
  }

  @Override
  public Policy _get_policy(int policy_type) {
    return null;
  }

  @Override
  public DomainManager[] _get_domain_managers() {
    return new DomainManager[0];
  }

  @Override
  public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
    return null;
  }
}

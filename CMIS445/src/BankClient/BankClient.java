package BankClient;

import BankService.Customer;
import BankService.TellerService;
import BankService.TellerServiceHelper;
import BankService.TellerServicePackage.AccountAlreadyExist;
import BankService.TellerServicePackage.AccountNotFound;
import BankService.TellerServicePackage.InsufficientFunds;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BankClient {

  static TellerService server;

  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    try {
      // Create and Initialize ORB Broker
      ORB broker = ORB.init(args, null);

      // Get Root Naming Context
      org.omg.CORBA.Object objRef = broker.resolve_initial_references("NameService");

      // Use INS NameContextExt
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // Resolve Object Reference in Naming Service
      server = TellerServiceHelper.narrow(ncRef.resolve_str("TellerService"));

      System.out.println("Connected to BankServer");

      // Begin Teller Program
      boolean loggedIn = false;
      Scanner in = new Scanner(System.in);


      do {
        System.out.print("Please Enter Username (Hint: user): ");
        String user = in.nextLine();
        System.out.print("Please Enter Your Pin (Hint: 0000): ");
        String pin = in.nextLine();
        System.out.println("Logging In");

        if(server.login(user.toLowerCase(), pin)) {
          loggedIn = true;
          new BankClient();
        } else {
          System.out.println("Incorrect User/PIN, try again");
        }

      } while (!loggedIn);

    } catch (InvalidName invalidName) {
      System.err.println("Thrown from BankClient.main");
      invalidName.printStackTrace();
    } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
      System.err.println("Thrown from BankClient.main");
      invalidName.printStackTrace();
    } catch (CannotProceed cannotProceed) {
      System.err.println("Thrown from BankClient.main");
      cannotProceed.printStackTrace();
    } catch (NotFound notFound) {
      System.err.println("Thrown from BankClient.main");
      notFound.printStackTrace();
    }
  }

  private BankClient() {
    System.out.println("Logged Into Remote Bank Server");

    Scanner in = new Scanner(System.in);
    boolean quit = false;
    int input;

    while(!quit) {
      System.out.println("\nSelect Option:\n" +
        "\t(1) Deposit Money\n" +
        "\t(2) Withdraw Money\n" +
        "\t(3) Transfer Money\n" +
        "\t(4) Cash Check\n" +
        "\t(5) View Account\n" +
        "\t(6) Lookup Account\n" +
        "\t(7) Add Account\n" +
        "\t(0) Quit\n");

      System.out.print("Choose > ");
      input = getNextInt();

      switch(input) {
        case 1 :
          deposit();
          break;
        case 2 :
          withdraw();
          break;
        case 3 :
          transfer();
          break;
        case 4 :
          cashCheck();
          break;
        case 5 :
          viewAccount();
          break;
        case 6 :
          lookupAccount();
          break;
        case 7 :
          addAccount();
          break;
        case 0 :
          quit = true;
          break;
        default :
          System.out.println("Invalid option, try again");
      }
    }
  }

  private void deposit() {
    System.out.print("Please Enter AccountID: ");
    int id = getNextInt();
    if(!server.checkAccount(id)) return;
    System.out.print("Please enter 1 for Checking 2 for Savings: ");
    int type = getNextInt();
    System.out.print("Please Enter Amount: ");
    double amount = getNextDouble();

    if(type == 1) {
      double balance = server.deposit(id, "checking", amount);
      System.out.println("New Checking Account Balance is: " + balance);
    } else if(type == 2) {
      double balance = server.deposit(id, "savings", amount);
      System.out.println("New Savings Account Balance is: " + balance);
    }
  }

  private void withdraw() {
    System.out.print("Please Enter AccountID: ");
    int id = getNextInt();
    if(!server.checkAccount(id)) return;
    System.out.print("Please enter 1 for Checking 2 for Savings: ");
    int type = getNextInt();
    System.out.print("Please Enter Amount: ");
    double amount = getNextDouble();

    if(!checkPIN(id)) return;

    try {
      if(type == 1) {
        double balance = server.withdraw(id, "checking", amount);
        System.out.println("New Checking Account Balance is: " + balance);
      } else if(type == 2) {
        double balance = server.withdraw(id, "savings", amount);
        System.out.println("New Savings Account Balance is: " + balance);
      }
    } catch (InsufficientFunds insufficientFunds) {
        System.out.println("Cannot withdraw, account has insufficient funds");
    }
  }

  private void cashCheck() {
    System.out.print("Please Enter AccountID: ");
    int id = getNextInt();
    if(!server.checkAccount(id)) return;
    System.out.print("Pleaes Enter Check Number: ");
    int chkNum = getNextInt();
    System.out.print("Please Enter Amount: ");
    double amount = getNextDouble();

    if(!checkPIN(id)) return;

    try {
      server.cashCheck(id, chkNum, amount);
      System.out.println("Check successfully cashed");
    } catch (InsufficientFunds insufficientFunds) {
      System.out.println("Cannot cash check, account has insufficient funds");
    }
  }

  private void transfer() {
    System.out.print("Please Enter AccountID: ");
    int id = getNextInt();
    if(!server.checkAccount(id)) return;
    System.out.print("Please enter 1 for Checking and 2 for Savings: ");
    int type = getNextInt();
    System.out.print("Please Enter Amount: ");
    double amount = getNextDouble();
    System.out.print("Enter Account to Transfer To: ");
    int toID = getNextInt();
    if(!server.checkAccount(id)) return;
    System.out.print("Please enter 1 for Checking and 2 for Savings: ");
    int toType = getNextInt();

    if(!checkPIN(id)) return;

    String accType = "checking";
    String toAccType = "checking";

    if(type == 2) accType = "savings";
    if(toType == 2) toAccType = "savings";

    try {
      double balance = server.transfer(id, accType, toID, toAccType, amount);
      System.out.println("New Account Balance After Transfer: " + balance);
    } catch (InsufficientFunds insufficientFunds) {
      System.out.println("Cannot transfer, account has insufficient funds");
    }
  }

  private void addAccount() {
    Customer newCustomer;
    do {
      newCustomer = new Customer();
      System.out.print("Please Enter Customer First Name: ");
      newCustomer.firstName = getNextString();
      System.out.print("Please Enter Customer Last Name: ");
      newCustomer.lastName = getNextString();
      System.out.print("Please Enter Customer SSN: ");
      newCustomer.SSN = getNextString();
      System.out.print("Please Enter Customer PIN: ");
      newCustomer.pin = getNextString();
      System.out.print("Please Enter Customer Initial Checking Deposit: ");
      newCustomer.checkingBalance = getNextDouble();
      System.out.print("Please Enter Customer Initial Savings Deposit: ");
      newCustomer.SavingsBalance = getNextDouble();

      System.out.println("\nNew Customer Preview: ");
      printCustomer(newCustomer);

      System.out.println("Enter 1 if this information is correct, any other to redo: ");
      int flag = getNextInt();
      if(flag == 1)
        try {
          server.createAccount(newCustomer);
          return;
        } catch (AccountAlreadyExist accountAlreadyExist) {
          System.out.println(accountAlreadyExist.message);
          return;
        }
    } while(true);


  }

  private void viewAccount() {
    System.out.print("Please Enter AccountID: ");
    int id = getNextInt();
    if(!server.checkAccount(id)) return;

    try {
      Customer c = server.getAccount(id);
      if(c == null) {
        System.out.println("Error while getting Account Information");
        return;
      }
      printCustomer(c);
    } catch (AccountNotFound accountNotFound) {
      System.out.println(accountNotFound.message);
    }
  }

  private void lookupAccount() {
    System.out.print("Please Enter Customer's SSN: ");
    String ssn  = getNextString();

    try {
      int id  = server.getAccountID(ssn);
      System.out.println("The Account ID for this SSN is: " + id);
    } catch (AccountNotFound anf) {
      System.out.println(anf.message);
      return;
    }
  }

  private int getNextInt() {
    do {
      try {
        return Integer.parseInt(in.readLine());
      } catch (IOException e) {
        System.out.println("Error thrown from getNextInt input");
        e.printStackTrace();
      } catch (NumberFormatException e) {
        System.out.print("Enter numbers only, please try again > ");
      }
    } while(true);
  }

  private double getNextDouble() {
    do {
      try {
        return Double.parseDouble(in.readLine());
      } catch (IOException e) {
        System.out.println("Error thrown from getNextInt input");
        e.printStackTrace();
      } catch (NumberFormatException e) {
        System.out.print("Enter numbers only, please try again > ");
      }
    } while(true);
  }

  private String getNextString() {
    String s = null;
    try {
      s = in.readLine();
      if(s.length() == 0) return null;
    } catch (IOException e) {
      System.out.println("Thrown from getNextString() on user input");
      e.printStackTrace();
    }
    return s;
  }

  private boolean checkPIN(int id) {
    int attempts = 0;
    do {
      attempts++;
      System.out.println("This transaction requires the customer's PIN");
      System.out.println("Please Enter PIN: ");
      String pin = getNextString();

      try {
        if(server.checkPIN(id, pin))
         return true;
      } catch (AccountNotFound accountNotFound) {
        return false;
      }

      if(attempts == 3) {
        System.out.println("You failed three times, must quit");
        return false;
      }
    } while (true);
  }

  private void printCustomer(Customer c) {
    System.out.println("Customer : " + c.firstName + " " + c.lastName);
    System.out.println("Account ID : " + c.accountID);
    System.out.println("SSN : " + c.SSN);
    System.out.println("PIN : " + c.pin);
    System.out.println("Checking Balance : " + c.checkingBalance);
    System.out.println("Savings Balance : " + c.SavingsBalance);
  }
}

package BankClient;

import BankService.TellerService;
import BankService.TellerServiceHelper;
import BankService.TellerServicePackage.AccountNotFound;
import BankService.TellerServicePackage.InsufficientFunds;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class BankClient {

  static TellerService server;

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

      System.out.println("Obtained Handle to Server: " + server);

      System.out.println(server.deposit(50000, "Checking", 2000));
      System.out.println(server.transfer(50000, "Checking", 50007, "Savings", 123.56));
      System.out.println(server.getSavingsBalance(50007));





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
    } catch (InsufficientFunds insufficientFunds) {
      insufficientFunds.printStackTrace();
    } catch (AccountNotFound accountNotFound) {
      accountNotFound.printStackTrace();
    }
  }
}

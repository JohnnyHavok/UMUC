/**
 * Created By: Justin Smith
 * Course: CMIS 445
 * Assignment: Final Project
 * Date: 03/23/14
 */
package BankServer;

import BankService.TellerService;
import BankService.TellerServiceHelper;
import DBTools.DBTools;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServer {
  public static void main(String[] args) {
    try {
      // Create ORB Broker
      ORB broker = ORB.init(args, null);

      // Get reference to Root POA and activate POAManager
      POA rootpoa = POAHelper.narrow(broker.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // Create Embedded Database connection
      Connection dbConnection = DBTools.dbConnect();

      // Create servant
      BankServant servant = new BankServant(dbConnection);

      // Get object reference from BankServant
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
      TellerService href = TellerServiceHelper.narrow(ref);

      // Get the Root Naming Context
      // Invoke naming service
      org.omg.CORBA.Object objRef = broker.resolve_initial_references("NameService");

      // Naming Service INS
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // Bind Object Reference in INS
      NameComponent path[] = ncRef.to_name("TellerService");
      ncRef.rebind(path, href);

      System.out.println("BankServer Running....");
      broker.run();

    } catch (InvalidName invalidName) {
      System.err.println("Thrown from BankServer.main");
      invalidName.printStackTrace();
    } catch (AdapterInactive adapterInactive) {
      System.err.println("Thrown from BankServer.main");
      adapterInactive.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Thrown from BankServer.main");
      e.printStackTrace();
    } catch (WrongPolicy wrongPolicy) {
      System.err.println("Thrown from BankServer.main");
      wrongPolicy.printStackTrace();
    } catch (ServantNotActive servantNotActive) {
      System.err.println("Thrown from BankServer.main");
      servantNotActive.printStackTrace();
    } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
      System.err.println("Thrown from BankServer.main");
      invalidName.printStackTrace();
    } catch (CannotProceed cannotProceed) {
      System.err.println("Thrown from BankServer.main");
      cannotProceed.printStackTrace();
    } catch (NotFound notFound) {
      System.err.println("Thrown from BankServer.main");
      notFound.printStackTrace();
    }
  }
}

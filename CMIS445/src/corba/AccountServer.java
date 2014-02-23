/*

File Name     :  <AccountServer.java>

Prepared by   :  <Mike Tarquinio>

Description   :  <This files contains the main for the AccountServer class.
                  It instantiates the ORB, creates the account object, and
                  binds the name in the naming service.  It also contains
                  the implementation code for the account class.>
    
*/


// The package containing our stubs.
import AccountModule.*;

// All CORBA applications need these classes.
import org.omg.CORBA.*;

// import the buffered writer class
import java.io.*;

/**
 <AccountServer - the main class for the AccountServer.  It contains the
                  main that initializes the ORB, creates the initial
                  account object, and writes the object reference
                  in string form to a file.>
 @author <Mike Tarquinio>
 @version <1.0>
 @see <AccountServer>
*/
public class AccountServer { 

   public static void main(String args[])  {    
      try{

         String socialsecuritynumber = "123-45-6789";
      
         // Create and initialize the ORB
         ORB orb = ORB.init(args, null);

         // Create the servant and register it with the ORB
         AccountServant helloRef = new AccountServant(socialsecuritynumber);

         orb.connect(helloRef);

         // Write the object reference to a file.
         String ref = null;
         ref = orb.object_to_string(helloRef);
         BufferedWriter out = new BufferedWriter(new 
                    FileWriter("hello.ref"));
         out.write(ref); 

         out.close();

         // Wait for invocations from clients
         java.lang.Object sync = new java.lang.Object();

         synchronized(sync){
            sync.wait();
            }

      } catch(Exception e) {

         System.err.println("ERROR: " + e);
          e.printStackTrace(System.out);
         }
   }
}

/**
 <AccountServant - the implementation class for the account object.>
 @author <Mike Tarquinio>
 @version <1.0>
 @see <AccountServer>
*/
class AccountServant extends _AccountImplBase {

   // private class variables
//   private int balance = 0;
	private static int balance = 0;
   private String socialsecuritynumber;
      
   /**
   * <AccountServant - constructor for the class.>
   *
   * <This method is the constructor for the new account.
   *
   * @param <string ssnumber> - <the accounts social security number>
   *
   * @see <AccountServant>
   */
   public AccountServant(String ssnumber) {
      socialsecuritynumber = ssnumber;
   }
   
   /**
   * <getbalance - get the balance for the user.>
   *
   * <This method returns the balance for the user.
   *
   * @see <getbalance>
   */
   public int getbalance () {
      return(balance);
   }

   /**
   * <deposit - add money to the account.>
   *
   * @param <int amount> - <the amount of money to add the account>
   *
   * <This method adds money to the account.
   *
   * @see <deposit>
   */   
   public void deposit (int amount) {
      balance = balance + amount;
   }

   /**
   * <withdrawl - pull money from the account.>
   *
   * @param <int amount> - <the amount of money to withdraw>
   *
   * <This method withdraws money from the account.
   *
   * @see <withdraw>
   */      
   public void withdrawl (int amount) {
      balance = balance - amount;
   }

   /**
   * <getsocialsecuritynumber - get the ss number.>
   *   
   * <This method gets the socialsecuritynumber.>
   *
   * @see <getsocialsecuritynumber>
   */   
   public String getsocialsecuritynumber () {
      return(socialsecuritynumber);
   }
}

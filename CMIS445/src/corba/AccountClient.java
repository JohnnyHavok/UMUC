/*

File Name     :  <AccountClient.java>

Prepared by   :  <Mike Tarquinio>

Description   :  <This files contains the main for the AccountClient class.
                  It instantiates the ORB and reads a file to 
                  get a reference to the account object.  It then
                  calls account methods.>
    
*/

import java.io.*;
import AccountModule.*;      // The package containing our stubs.
import org.omg.CORBA.*;      // All CORBA applications need these classes.

/**
 <AccountClient - the main class for the AccountClient.  It contains the
                  main that initializes the ORB and reads the
                  hello.ref to get a reference to the account object.  
                  It then calls account methods.>
 @author <Mike Tarquinio>
 @version <1.0>
 @see <AccountClient>
*/
public class AccountClient
{
  public static void main(String args[])
  {
     try{

        // Create and initialize the ORB
        ORB orb = ORB.init(args, null);

        // Get the reference to the Account Server object
        String ref = null;
        BufferedReader in=new BufferedReader(new FileReader("hello.ref"));
        ref = in.readLine();
        org.omg.CORBA.Object obj = orb.string_to_object(ref);
        Account myaccount = AccountHelper.narrow(obj);

        // Call the Account server object and print results
        String pssnumber = myaccount.getsocialsecuritynumber();
        System.out.println(pssnumber);

        // print out the new account balance
        System.out.println("Balance for account: " + pssnumber + " is: " +
           myaccount.getbalance());
  
        // add 15 dollars to the account
        myaccount.deposit(15);

        // print out the new account balance
        System.out.println("Balance for account: " + pssnumber + " is: " +
           myaccount.getbalance()); 
      
        // add 25 dollars to the account
        myaccount.deposit(25);

        // print out the new account balance
        System.out.println("Balance for account: " + pssnumber + " is: " +
           myaccount.getbalance());
      
        // withdrawl 5 dollars
        myaccount.withdrawl(5);

        // print out the new account balance
        System.out.println("Balance for account: " + pssnumber + " is: " +
           myaccount.getbalance());
     } catch(Exception e) {
         System.out.println("ERROR : " + e);
         e.printStackTrace(System.out);
        }
  }
}



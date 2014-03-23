package BankClient;

import BankService.AuthTeller;
import BankService.AuthTellerHelper;
import org.omg.CORBA.ORB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BankClient {
  public static void main(String[] args) {
    ORB broker = ORB.init(args, null);

    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader("server.ref"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String ref = null;
    if (in != null) {
      try {
        ref = in.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    org.omg.CORBA.Object obj = broker.string_to_object(ref);

    AuthTeller at = AuthTellerHelper.narrow(obj);

    System.out.println(at.login("jsmith", "1234"));


  }
}

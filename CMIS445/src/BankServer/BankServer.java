package BankServer;

import DBTools.DBTools;
import org.omg.CORBA.ORB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class BankServer {
  public static void main(String[] args) {

    try {
      Connection conn = DBTools.dbConnect();
      ORB broker = ORB.init(args, null);

      BankServant server = new BankServant(conn);

      broker.connect(server);

      String ref = broker.object_to_string(server);
      BufferedWriter out = new BufferedWriter(new FileWriter("server.ref"));
      out.write(ref);
      out.close();

      java.lang.Object sync = new Object();

      synchronized (sync) { sync.wait(); }

    } catch (SQLException e) {
      System.err.println("Unhandled SQL Exception reached BankServer.main");
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println("FileWriter Exception in BankServer.main");
      System.err.println(e.getMessage());
    } catch (InterruptedException e) {
      System.err.println("SYNC Exception in BankServer.main");
      System.err.println(e.getMessage());
    }

  }
}

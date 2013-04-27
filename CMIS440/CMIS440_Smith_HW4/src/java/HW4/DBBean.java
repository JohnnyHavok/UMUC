/**
 *   Document   : DBBean
 *   Created on : Apr 27, 2013, 2:46:19 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Homework 4
 */

package HW4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Justin Smith
 */
@ManagedBean
public class DBBean {
    public DBBean() { }
    
    public ArrayList<TableRow> getCatalog() {
        Connection c = getConnection();
        ResultSet rset = null;
        ArrayList<TableRow> result = new ArrayList<TableRow>();
        
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Catalog_T");
            rset = ps.executeQuery();
            while(rset.next()) {
                TableRow row = new TableRow();
                row.setId(rset.getInt("ID"));
                row.setName(rset.getString("Name"));
                row.setDesc(rset.getString("Description"));
                row.setPrice(rset.getDouble("Price"));
                row.setQty(rset.getInt("QuantityInStock"));
                result.add(row);
            }
            ps.close();
        } catch (Exception e) {
            System.err.println("Exception thrown from DBBean/getCatalog()");
            e.printStackTrace();
        } finally {
            try {
                if(rset != null)
                    rset.close();                
                if(!c.isClosed())
                    c.close();
            } catch (Exception e) {
                System.err.println("Exception thrown from DBBean/getCatalog()");
                e.printStackTrace();
            }
        }
        return result;
    }
    
    private Connection getConnection() {
        Connection c = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/novaDB");
            c = ds.getConnection();
        } catch (Exception e) {
            System.err.println("Exception thrown from DBBean/getConnection()");
            e.printStackTrace();
        }
        
        return c;
    }
}

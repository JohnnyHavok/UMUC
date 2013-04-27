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
    
    public void deleteItem(int pk) {
        Connection c = getConnection();
        System.out.println("Attempting to delete item: "+pk);
        try {
            PreparedStatement ps = c.prepareStatement("DELETE FROM Catalog_T where id=?");
            ps.setInt(1, pk);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.err.println("Exception thrown from DBBean/deleteItem()");
            e.printStackTrace();
        } finally {
            try {
                if(!c.isClosed())
                    c.close();
            } catch (Exception e) {
                System.err.println("Exception thrown from DBBean/deleteItem()");
                e.printStackTrace();
            }
        }
    }
    
    public void modifyItem(TableRow tr) {
        Connection c = getConnection();
        System.out.println("Attempting to modify item: "+tr.getId());
        try {
            PreparedStatement ps = c.prepareStatement("UPDATE Catalog_T SET "
                    + "Name = ?,"
                    + "Description = ?,"
                    + "Price = ?,"
                    + "QuantityInStock = ? "
                    + "WHERE ID = ?");
            
            ps.setString(1, tr.getName());
            ps.setString(2, tr.getDesc());
            ps.setDouble(3, tr.getPrice());
            ps.setInt(4, tr.getQty());
            ps.setInt(5, tr.getId());
            ps.executeUpdate();
            ps.close();            
        } catch (Exception e) {
            System.err.println("Exception thrown from DBBean/modifyItem()");
            e.printStackTrace();
        } finally {
            try {
                if(!c.isClosed())
                    c.close();
            } catch (Exception e) {
                System.err.println("Exception thrown from DBBean/modifyItem()");
                e.printStackTrace();
            }
            
        }
    }
    
    public TableRow getItem(int pk) {
        TableRow row = new TableRow();
        Connection c = getConnection();
        ResultSet rset = null;
        System.out.println("Attempting to get item: "+pk);
        
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Catalog_T "
                    + "WHERE ID=?");
            ps.setInt(1, pk);
            rset = ps.executeQuery();
            while(rset.next()) {
                row.setId(rset.getInt("ID"));
                row.setName(rset.getString("Name"));
                row.setDesc(rset.getString("Description"));
                row.setPrice(rset.getDouble("Price"));
                row.setQty(rset.getInt("QuantityInStock"));
            }
        } catch (Exception e) {
            System.err.println("Exception thrown from DBBean/getItem()");
            e.printStackTrace();
        } finally {
            try {
                if(rset != null)
                    rset.close();                
                if(!c.isClosed())
                    c.close();
            } catch (Exception e) {
                System.err.println("Exception thrown from DBBean/getItem()");
                e.printStackTrace();
            }
        }
        
        return row;
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

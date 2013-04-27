/**
 *   Document   : TableRow
 *   Created on : Apr 27, 2013, 4:48:13 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Homework 4
 */

package HW4;

/**
 *
 * @author Justin Smith
 */
public class TableRow {  
    // Doing everything short of implementing a DAO
    private int id;
    private String name;
    private String desc;
    private double price;
    private int qty;

    public TableRow() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }   
}

/**
 *   Document   : InventoryItem
 *   Created on : Apr 21, 2013, 4:38:20 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Project 2
 */

package project2;

/**
 * Inventory Class - Part of Data Model
 * @author Justin Smith
 */
public class InventoryItem {
    private String id, name, desc;
    private double price;
    private int qty;
    
    public InventoryItem() {    }

    public InventoryItem(String id, String name, String desc, double price, int qty) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

/**
 *   Document   : Inventory
 *   Created on : Apr 21, 2013, 4:58:37 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Project 2
 */

package project2;

import java.util.ArrayList;

/**
 * Inventory System Bean
 * @author Justin Smith
 */
public class Inventory {
    public ArrayList<InventoryItem> inventory;
    
    public Inventory() { inventory = new ArrayList<InventoryItem>(); }
    
    public void clearInventory() {
        inventory = new ArrayList<InventoryItem>();
    }
    
    public void insertInventory(InventoryItem item) {
        inventory.add(item);
    }
    
    public ArrayList getInventory() { return inventory; }

}

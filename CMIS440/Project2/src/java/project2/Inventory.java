/**
 *   Document   : Inventory
 *   Created on : Apr 21, 2013, 4:58:37 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Project 2
 */

package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Inventory System Bean
 * @author Justin Smith
 */
public class Inventory {
    ArrayList<InventoryItem> inventory;
    String iFile = "ItemsCatalog.txt";
    
    public Inventory() { }

}

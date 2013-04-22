/**
 *   Document   : InventoryServlet
 *   Created on : Apr 21, 2013, 5:57:50 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Project 2
 */

package project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Controller for MVC M2 pattern.
 * @author Justin Smith
 */
public class InventoryServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Inventory inventory = new Inventory();
        RequestDispatcher rd;
        
        try {
            String inputLine;
            
            System.out.println(new java.io.File("").getAbsolutePath());
            
            ServletContext con = getServletContext();
            InputStream in = con.getResourceAsStream("/data/ItemsCatalog.txt");
            InputStreamReader inr = new InputStreamReader(in);
            
            BufferedReader reader = new BufferedReader(inr);
            
            // Read all Inventory Items and add them to the arary list
            while((inputLine = reader.readLine()) != null) {
                String t[] = inputLine.split(":");
                InventoryItem item = new InventoryItem(t[0], t[1], t[2], 
                        Double.parseDouble(t[3]), Integer.parseInt(t[4]));
                inventory.insertInventory(item);
                
                System.out.println(t[0]+":"+t[1]+":"+t[2]+":"+t[3]+":"+t[4]);
            }
            
            // Include Catalog view.
            rd = request.getRequestDispatcher("catalogview.jsp");
            rd.forward(request, response);
            
            
            
        } catch (Exception e) {
            System.err.println(e);
        }
    } 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

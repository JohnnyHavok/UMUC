/**
 *   Document   : InventoryServlet
 *   Created on : Apr 21, 2013, 5:57:50 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : 
 */

package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
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
        try {
            ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
            String inputLine;
            
            System.out.println(new java.io.File("").getAbsolutePath());
            
            FileReader file = new FileReader("data/ItemsCatalog.txt");
            BufferedReader reader = new BufferedReader(file);
            
            // Read all Inventory Items and add them to the arary list
            while((inputLine = reader.readLine()) != null) {
                String t[] = inputLine.split(":");
                InventoryItem item = new InventoryItem(t[1], t[2], t[3], 
                        Double.parseDouble(t[4]), Integer.parseInt(t[5]));
                inventory.add(item);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        
        
        
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet InventoryServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet InventoryServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally { 
//            out.close();
//        }
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

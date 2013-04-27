/**
 *   Document   : ModServlet
 *   Created on : Apr 27, 2013, 6:08:38 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Homework 4
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package HW4;

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
public class ModServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DBBean dbean = new DBBean();
        if(request.getParameter("del") != null) { // User clicked delete
            int id = Integer.parseInt(request.getParameter("sel"));
            dbean.deleteItem(id);
            
        } else if(request.getParameter("mod") != null) { // User clicked modify
            int id = Integer.parseInt(request.getParameter("sel"));
            TableRow result = dbean.getItem(id);
            request.setAttribute("results", result);
            request.getRequestDispatcher("/moditem.jsp").forward(request, response);
            
            
        } else if(request.getParameter("update") != null) { // User wants to update item
            TableRow row = new TableRow();
            row.setId(Integer.parseInt(request.getParameter("id")));
            row.setName(request.getParameter("name"));
            row.setDesc(request.getParameter("desc"));
            row.setPrice(Double.parseDouble(request.getParameter("price")));
            row.setQty(Integer.parseInt(request.getParameter("qty")));
            
            dbean.modifyItem(row);
        }
        
        // Update results and return user to admin page
        ArrayList<TableRow> result = dbean.getCatalog();
        request.setAttribute("results", result);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
        

        
        
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

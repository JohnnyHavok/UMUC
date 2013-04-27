/**
 *   Document   : LoginServlet
 *   Created on : Apr 27, 2013, 2:51:35 PM
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Justin Smith
 */
public class LoginServlet extends HttpServlet {   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher view = null;
        
        if(request.getParameter("username").equals("admin") &&
           request.getParameter("password").equals("admin") ) {

            System.out.println("Admin Login Sucessful");

            view = request.getRequestDispatcher("admin.jsp");
            view.forward(request, response);
            
        } else if(request.getParameter("username").equals("user") &&
                  request.getParameter("password").equals("user") ) {
            
            System.out.println("User Login Sucessful");

            view = request.getRequestDispatcher("user.jsp");
            view.forward(request, response);
        } else {
            System.out.println("Login Attempt Failed");
            
            view = request.getRequestDispatcher("failedlogin.jsp");
            view.forward(request, response);
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

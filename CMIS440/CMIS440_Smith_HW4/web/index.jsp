<%-- 
    Document   : index
    Created on : Apr 26, 2013, 8:25:29 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 4
--%>

<%@page import="java.sql.*"%>
<%@page import="oracle.jdbc.pool.OracleDataSource"%>

<% 
    Connection conn=null;
    Statement stmt=null;
    ResultSet rset=null;
%>
    



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Oracle DB connection</title>
    </head>
    <body>
        <%
        try {
            OracleDataSource ds = new OracleDataSource();
            ds.setURL("jdbc:oracle:thin:@nova.umuc.edu:1521:acad");
            conn = ds.getConnection("cm440b17", "_NEED_PASSWORD_");
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM Catalog_T");
        %>
        
        <table>
            <tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Quantity</th></tr>
            <% while(rset.next()) { %>
                <tr>
                    <td><%= rset.getString("ID") %></td>
                    <td><%= rset.getString("Name") %></td>
                    <td><%= rset.getString("Description") %></td>
                    <td><%= rset.getString("Price") %></td>
                    <td><%= rset.getString("QuantityInStock") %></td>
                </tr>
            <% } %>
        </table>
        
        <% } catch(SQLException e) {
            System.err.println("SQL Exception thrown: " + e.getMessage());
        } finally {
            if(rset != null) rset.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        %>  
    </body>
</html>

<%-- 
    Document   : user
    Created on : Apr 27, 2013, 3:04:26 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 4
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Catalog View</title>
    </head>
    <body>
        <h1>Product Catalog View (Read Only)</h1>
        <h2>Current Catalog Data</h2>
        
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <c:forEach items="${results}" var="r">
                <tr>
                    <td><c:out value="${r.id}"/></td>
                    <td><c:out value="${r.name}"/></td>
                    <td><c:out value="${r.desc}"/></td>
                    <td><c:out value="${r.price}"/></td>
                    <td><c:out value="${r.qty}"/></td>
                </tr>
            </c:forEach>          
        </table>
        
        <form action="LogoutServlet" method="post">
            <input type="submit" value="Logout" />
        </form>
        
    </body>
</html>

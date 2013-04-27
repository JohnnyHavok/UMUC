<%-- 
    Document   : moditem
    Created on : Apr 27, 2013, 7:01:35 PM
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
        <title>Modify Item</title>
    </head>
    <body>
        <h1>Modify Item</h1>
        
        <form action="ModServlet" method="post">
            <input type="hidden" name="id" value="<c:out value="${results.id}"/>" />
            <table border ="1">
                <tr>
                    <td>ID</td>
                    <td><c:out value="${results.id}" /></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><input type="text" width="45" name="name" value="<c:out value="${results.name}"/>" /></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><input type="text" width="45" name="desc" value="<c:out value="${results.desc}"/>" /></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="text" width="45" name="price" value="<c:out value="${results.price}"/>" /></td>
                </tr>
                <tr>
                    <td>Quantity In Stock</td>
                    <td><input type="text" width="45" name="qty" value="<c:out value="${results.qty}"/>" /></td>
                </tr>
            </table>
                <input type="submit" value="Update" name="update" />
                <input type="submit" value="Cancel" name="cancel" />
        </form>
    </body>
</html>

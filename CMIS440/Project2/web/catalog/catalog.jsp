<%-- 
    Document   : catalog
    Created on : Apr 21, 2013, 3:14:01 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Project 2
--%>

<jsp:useBean id = "inventory"
             scope = "session"
             class = "project2.Inventory" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parts Inventory Catalog</title>
    </head>
    <body>
        <form action='../logout.jsp' method='post'>
            <input type='submit' value='Log Off' name='logoff'/>
            
        </form>
        
    </body>
</html>

<%-- 
    Document   : logout
    Created on : Apr 21, 2013, 4:12:48 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Project 2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logging Out</title>
    </head>
    <body>
        <% session.invalidate(); %>
        <h1>'<%= request.getRemoteUser() %>' has been logged out</h1>
        <a href='index.jsp'>Click here to log back in</a>
        
    </body>
</html>

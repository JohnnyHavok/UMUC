<%-- 
    Document   : login_error
    Created on : Apr 21, 2013, 3:04:09 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Project 2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Error!</title>
    </head>
    <body>
        <h1>Invalid username and/or password!</h1>
        <a href='<%= response.encodeURL("login.jsp") %> '>Please Try Again</a>
    </body>
</html>

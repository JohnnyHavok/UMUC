<%-- 
    Document   : failedlogin
    Created on : Apr 27, 2013, 3:04:21 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 4
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Error</title>
    </head>
    <body>
        <h1>Login Error Detected</h1>
        <p>
            The following credentials were invalid:
    <ui>
        <li>Username: <%= request.getParameter("username") %> </li>
        <li>Password: <%= request.getParameter("password") %> </li>
    </ui>
        </p>
        <p>
            <a href="index.jsp">Please Try Again</a>
        </p>
    </body>
</html>


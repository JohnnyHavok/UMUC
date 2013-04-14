<%-- 
    Document   : index
    Created on : Apr 14, 2013, 1:51:54 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Please Login</title>
    </head>
    <body>
        <h1>Homework 3</h1>
        <h2>Please Login</h2>
        <form action="LoginServlet" method="post">
            <p>
                <label>Username: </label>
                <input type="text" name="username" size="15" />
            </p>
            
            <p>
                <label>Password: </label>
                <input type="password" name="password" size="15" />
            </p>
            
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>

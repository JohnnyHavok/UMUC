<%-- 
    Document   : index
    Created on : Apr 26, 2013, 8:25:29 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 4
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homework 4</title>
    </head>
    
    <body>
        <h1>Homework 4</h1>
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
            
            <p>
                Hints:<br/>
                User access: user/user <br/>
                Admin access: admin/admin <br/>
            </p>
                
        </form>
    </body>
</html>

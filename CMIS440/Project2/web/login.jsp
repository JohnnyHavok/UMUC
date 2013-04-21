<%-- 
    Document   : login
    Created on : Apr 21, 2013, 2:52:04 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Project 2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Please Login</title>
    </head>
    <body>
        <h1>Project 2</h1>
        <h2>Please Login</h2>
        <form action='<%= response.encodeURL("j_security_check") %>' method="post">
            <p>
                <label>Username: </label>
                <input type="text" name="j_username" size="15" />
            </p>
            
            <p>
                <label>Password: </label>
                <input type="password" name="j_password" size="15" />
            </p>
            
            <input type="submit" value="Submit" />
            
            <p>
                Hint: user/user or admin/admin
            </p>
                
        </form>
        
    </body>
</html>

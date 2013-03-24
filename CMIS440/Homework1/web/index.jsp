<%-- 
    Document   : index
    Created on : Mar 24, 2013, 6:17:34 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ThreeParams Homework 1 Assignment</title>
    </head>
    <body>
        <h1>ThreeParams</h1>
        <form action="Homework1" method="get">
            <p>
                <label>Param 1</label>
                <input type="text" name="Param1" size="15"/>
            </p>
            
            <p>
                <label>Param 2</label>
                <input type="text" name="Param2" size="15"/>
            </p>
            
            <p>
                <label>Param 3</label>
                <input type="text" name="Param3" size="15"/>
            </p>
            
            <p>
                <input type="submit" Value="Submit"/>
            </p>
            
        </form>
    </body>
</html>

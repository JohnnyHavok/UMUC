<%-- 
    Document   : RegResult
    Created on : Apr 7, 2013, 6:01:30 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Project 1
--%>

<jsp:useBean id = "person"
             scope = "session"
             class = "project1.Person" />

<% 
    String salutation = person.getSalutation();
    String fname = person.getFname();
    String lname = person.getLname();
    String age = person.getAge().toString();
    String email = person.getEmail();
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmation Page</title>
    </head>
    <body>
        <h1>Confirmation Page</h1>
        
        <table border=0>
            <tr><td align="right">Salutation:</td><td><%= salutation %></td></tr>
            <tr><td align="right">First Name:</td><td><%= fname %></td></tr>
            <tr><td align="right">Last Name:</td><td><%= lname %></td></tr>
            <tr><td align="right">Age:</td><td><%= age %></td></tr>
            <tr><td align="right">Email Address:</td><td><%= email %></td></tr>
        </table>
        <a href="index.xhtml">Back to Registration Page</a> 
        
    </body>
</html>

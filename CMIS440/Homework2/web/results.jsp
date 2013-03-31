<%-- 
    Document   : index
    Created on : Mar 30, 2013, 6:29:28 PM
    Author     : Justin Smith
    Course     : CMIS 440
    Project    : Homework 2
--%>

<%
  String param1 = request.getParameter("Param1");
  String param2 = request.getParameter("Param2");
  String param3 = request.getParameter("Param3");
%>

<jsp:useBean id    = "counter" 
             scope = "session" 
             class = "HW2.CountBean" />

<% counter.incrementCount(); %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homework 2 Results</title>
    </head>
    <body>
        <h1>Homework 2 ThreeParams Results</h1>
        <table border=1>
            <tr><td>Parameter 1</td><td><%= param1 %></td></tr>
            <tr><td>Parameter 2</td><td><%= param2 %></td></tr>
            <tr><td>Parameter 3</td><td><%= param3 %></td></tr>
        </table>
        
        <h1>Session Counter</h1>
        <p>Runs this session: <%= counter.getCount() %> </p>
        <p>Session ID: <%= session.getId() %> </p>
        

 
        
    </body>
</html>

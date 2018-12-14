<!DOCTYPE html>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %>
<%@ page import="servlet.Request" %>
<%@page import="javax.servlet.http.HttpServletRequest" %>
<%@page pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: easyd
  Date: 14.12.2018
  Time: 8:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Результаты проверки</title>
    <meta charset="UTF-8">
    <meta name="author" content="Mikhail Gostev & Pavel Kotelevsky">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
<table>
    <tr>
        <td id="header" colspan="2">
            <p id="name">Лабораторная работа №2 <br>
                Котелевский Павел Георгиевич P3201 Вариант 18108</p>
        <td>
    </tr>
    <tr>
        <td>Результаты проверки</td>
    </tr>
        <%
        if (((Boolean)session.getAttribute("correct")).booleanValue() == false) {
          out.print("<tr><td>Ошибка. Введены неверные значения.</td></tr>");
          out.print("</table></body></html>");
        }
        else {
          out.print("<table class=\\\"inner\\\"><tr><th>Координата Х</th><th>Координата Y</th><th>Значение R</th><th>Результат</th></tr>");
          List<Request> results = (List<Request>)session.getAttribute("current");
          List<Request> history = (List<Request>)session.getAttribute("history");
          for (Request r : results) { %>
            <tr><td><% out.print(r.x); %></td><td><% out.print(r.y); %></td><td><% out.print(r.r); %></td>
            <td><% out.print(r.check ? "Да" : "Нет"); %> </td></tr>

            <% }
        }
        %>
    <tr><th colspan=4>История запросов</th></tr>
    %>
</table>
</body>
</html>

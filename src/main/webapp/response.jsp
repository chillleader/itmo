!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="servlet.Request" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>Результаты проверки</title>
    <meta charset="UTF-8">
    <meta name="author" content="Mikhail Gostev & Pavel Kotelevsky">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--<link rel="stylesheet" href="css/main.css">--%>
    <style>
        <%@include file='css/style.css' %>
    </style>
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
    <% if (!((Boolean) session.getAttribute("correct"))) { %>
    <%= "<tr><td>Ошибка. Введены неверные значения.</td></tr></table></body></html>" %>
    <% } else { %>
    <%= "<table class=\"" + "inner" + "\"><tr><th>Координата Х</th><th>Координата Y</th><th>Значение R</th><th>Результат</th></tr>" %>
    <%
            final String tableRowFormat = "<tr>"
                    + "<td>%f</td>"
                    + "<td>%f</td>"
                    + "<td>%f</td>"
                    + "<td>%s</td>"
                    + "</tr>";

            StringBuilder table = new StringBuilder();
            List<Request> history = (List<Request>) session.getAttribute("history");

            if (history != null) {
                for (Request req : history) {
                    table.append(String.format(tableRowFormat, req.x, req.y, req.r,
                            req.check ? "True" : "False"));
                }
            }
        }
    %>
    <tr>
        <th colspan=4>История запросов</th>
    </tr>
</table>
</body>
</html>
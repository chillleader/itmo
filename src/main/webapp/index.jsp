<%@ page import="java.util.List" %>
<%@ page import="servlet.Request" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Mikhail Gostev & Pavel Kotelevsky">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ЛР №2</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
    <%--<link rel=stylesheet type="text/css" href="css/style.css">--%>

    <script type="text/javascript"> <%@include file='js/form_validator.js' %> </script>
    <script type="text/javascript"> <%@include file='js/plot.js' %> </script>

    <%--<script type="text/javascript" src="js/form_validator.js"></script>--%>
    <%--<script type="text/javascript" src="js/plot.js"></script>--%>
</head>
<body onload="drawCanvas(1)">
<table>
    <tr>
        <td id="header" colspan="3">
            <p id="name">
                Лабораторная работа №2<br>
                Группу P3201, Вариант 18141<br>
                Котелевский Павел Георгиевич<br>
                Гостев Михаил Владимирович
            </p>
        <td>
    </tr>
    <tr id="content">
        <td id="left-content-column">
            <table class="inner">
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Result</th>
                </tr>

                <%
                    final String tableRowFormat = "<tr>"
                            + "<td>%f</td>"
                            + "<td>%f</td>"
                            + "<td>%f</td>"
                            + "<td>%s</td>"
                            + "</tr>";

                    StringBuilder table = new StringBuilder();

                    try {
                        List<Request> history = (List<Request>) session.getAttribute("history");
                        if (history != null) {
                            for (Request req : history) {
                                table.append(String.format(tableRowFormat, req.x, req.y, req.r,
                                        req.check ? "True" : "False"));
                            }
                        }
                    } catch (IllegalStateException ex) {
                    }
                %>
                <%= table.toString() %>
            </table>
        </td>
        <td id="middle-content-column">
            <form id="data-form" name="data"
                  onsubmit="return checkForm();"
                  method="POST" action="${pageContext.request.contextPath}">
                <fieldset>
                    <legend>Значения Х</legend>
                    <div id="xs_checkboxes">
                        <label><input type="checkbox" name="x" value="-5" id="x0">-5</label><br>
                        <label><input type="checkbox" name="x" value="-4" id="x1">-4</label><br>
                        <label><input type="checkbox" name="x" value="-3" id="x2">-3</label><br>
                        <label><input type="checkbox" name="x" value="-2" id="x3">-2</label><br>
                        <label><input type="checkbox" name="x" value="-1" id="x4">-1</label><br>
                        <label><input type="checkbox" name="x" value="0" id="x5"> 0</label><br>
                        <label><input type="checkbox" name="x" value="1" id="x6"> 1</label><br>
                        <label><input type="checkbox" name="x" value="2" id="x7"> 2</label><br>
                        <label><input type="checkbox" name="x" value="3" id="x8"> 3</label><br>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Значение Y</legend>
                    Введите значение в промежутке [-5; 5]
                    <p><input type="text" name="y" required>
                </fieldset>
                <fieldset>
                    <legend>Значения R</legend>
                    Введите значение в промежутке [1; 4]
                    <p><input oninput="onRadiusInput()" type="text" name="r" required>
                </fieldset>
                <div id="errors"></div>
                <input type="submit" value="Отправить">
            </form>
        </td>
        <td id="right-content-column">
            <p>Приложение определяет, входят ли указанные пользователем точки в заданную
                область.</p>

            <!--&#45;&#45;<svg id="plot-svg" width="380" height="420">
      <circle r="90" cx="190" cy="210" fill="rgb(51, 153, 255)"></circle>
      <polygon points="190,210 190,30 370,210" fill="rgb(51, 153, 255)"></polygon>
      <polygon points="190,210 190,390 370,390 370,210" fill="white"></polygon>
      <polygon points="190,210 190,300 10,300 10,210" fill="rgb(51, 153, 255)"></polygon>
      <line x1="190" x2="190" y1="0" y2="420" stroke="black" stroke-width="3"></line>
      <line x1="0" x2="380" y1="210" y2="210" stroke="black" stroke-width="3"></line>
      <text id="svg_text_r" x="200" y="240">R = 1</text>
    </svg>&#45;&#45;-->
            <canvas id="canvas" onclick="onCanvasClick()"
                    style="background-color:#ffffff; border-radius: 20px;" width="300"
                    height="300"></canvas>
        </td>

    </tr>
    <tr>
        <td id="footer" colspan="3"><p id="footer-text">&copy; Университет ИТМО 2018</p></td>
    </tr>
</table>
</body>
</html>
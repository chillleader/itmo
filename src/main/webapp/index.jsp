<%@page contentType="text/html" pageEncoding="UTF-8"
        language="java"
        import="java.util.List, java.util.ArrayList, servlet.ControllerServlet servlet.AreaCheckServlet"
        session="true"
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Mikhail Gostev & Pavel Kotelevsky">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ЛР №2</title>

    <link rel="stylesheet" href="css/main.css">
    <script type="text/javascript" src="js/validator.js"></script>
</head>
<body>
<table>
    <tr>
        <td id="header" colspan="3">
            <p id="name">
                Лабораторная работа №2<br>
                Вариант 18141<br>
                Котелевский Павел Георгиевич P3201<br>
                Гостев Михаил Владимирович P3201
            </p>
        <td>
    </tr>
    <tr id="content">
        <td id="col1">
            Приложение определяет, входят ли указанные пользователем точки в заданную область.
        </td>
        <td id="col2">
            <form name="data" onsubmit="return checkForm();" method="post" action="script.php">
                <fieldset>
                    <legend>Значения Х</legend>
                    <label><input type="checkbox" name="x[]" value="-4" id="x0"> - -4</label><br>
                    <label><input type="checkbox" name="x[]" value="-3" id="x1"> - -3</label><br>
                    <label><input type="checkbox" name="x[]" value="-2" id="x2"> - -2</label><br>
                    <label><input type="checkbox" name="x[]" value="-1" id="x3"> - -1</label><br>
                    <label><input type="checkbox" name="x[]" value="0" id="x4"> - 0</label><br>
                    <label><input type="checkbox" name="x[]" value="1" id="x5"> - 1</label><br>
                    <label><input type="checkbox" name="x[]" value="2" id="x6"> - 2</label><br>
                    <label><input type="checkbox" name="x[]" value="3" id="x7"> - 3</label><br>
                    <label><input type="checkbox" name="x[]" value="4" id="x8"> - 4</label><br>
                </fieldset>
                <fieldset>
                    <legend>Значение Y</legend>
                    Введите значение (-3; 3)
                    <p><input type="text" name="y" required>
                </fieldset>
                <fieldset>
                    <legend>Значения R</legend>
                    <label><input type="checkbox" name="r[]" value="1" id="y0"> - 1</label><br>
                    <label><input type="checkbox" name="r[]" value="1.5" id="y1"> - 1.5</label><br>
                    <label><input type="checkbox" name="r[]" value="2" id="y2"> - 2</label><br>
                    <label><input type="checkbox" name="r[]" value="2.5" id="y3"> - 2.5</label><br>
                    <label><input type="checkbox" name="r[]" value="3" id="y4"> - 3</label><br>
                </fieldset>
                <div id="errors"></div>
                <input type="submit" value="Отправить">
            </form>
        </td>
        <td id="col3">
            <img src="areas.png">
        </td>

    </tr>
    <tr>
        <td id="footer" colspan="3"><p id="footer-text">&copy; Университет ИТМО 2018</p></td>
    </tr>
</table>
</body>
</html>
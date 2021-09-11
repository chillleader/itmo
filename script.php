<?php
session_start();
?>
<!DOCTYPE html>
	<html>
	<head>
	<title>Результат</title>
	
	<style type="text/css">

body{
	background-color: #d9d8e3;
	/* наследование */
	color: #26364a;
	font-family: monospace;
}
table {
	text-align: center;
}
/* селектор дочерних элементов*/
td>form {
	text-align: center;
}
fieldset {
	text-align: left;
	border: 3px solid #4d7198;
	margin-bottom: 7px;
}
input {
	margin: 2px 7px;
}
/* селектор потомств - граница для картинок в таблицах */
td img {
	border: 3px solid #4d7198;
}
/* каскадирование */
table.inner {
	border: 3px solid #4d7198;
	border-collapse: collapse;
	text-align: left;
	margin: auto;
}
table.inner td {
	border: 1px solid #4d7198;
}
table.inner th {
	border-bottom: 3px solid #4d7198;
	border-top: 3px solid #4d7198;
	text-align: center;
	border-right: 1px solid #4d7198;
	padding: 7px;
}
#header{
	text-align: center;
	height: 80px;
	width: 100%;
	vertical-align: middle;
	background-color: #4d7198;
	border-radius: 15px;
}
#name{
	font-size: 25px;
	color: white;
	font-family: serif;
}
tr {
	padding-top: 5px;
}
td{
	vertical-align: top;
	padding: 5px;
}
#col1{
	width: 25%;
	text-align: center;
}
#col2{
	width: 50%;

}
#col3{
	width: 25%;
	text-align: center;
}
#footer{
	text-align: center;
}
/* селектор атрибутов - внешний вид кнопки */
input[type="submit"], input[type="button"] {
	background-color: #4d7198;
	border-radius: 10px;
	color: white;
	font-family: monospace;
	padding: 3px 7px;
}
input[type="submit"]:hover, input[type="button"]:hover {
	background-color: #688bb0;
}

	</style>
	<script type="text/javascript">
	<!--
  	 	function reset(){
			var req = new XMLHttpRequest;
			req.open('POST', 'destroy.php');
			req.send(null);
			location.reload();
    	}
 	//-->
	</script>

	</head>
	<body>
	<table>
	<tr><td id="header" colspan="2">
		<p id="name">Лабораторная работа №1 <br>
			Котелевский Павел Георгиевич P3201 Вариант 18108</p>
	<td></tr>
	<tr><td>Результаты проверки</td></tr>


<?php

class Request {
	public $x = "";
	public $y = "";
	public $r = "";
	public $t = "";
	public $in = "";

	public function __construct($xval, $yval, $rval, $inval, $timeval) {
       $this->x = $xval;
       $this->y = $yval;
       $this->r = $rval;
       $this->t = $timeval;
       $this->in = $inval;
   	}
}

if (!isset($_SESSION['history'])) $_SESSION['history'] = array();
$history = $_SESSION['history'];

//var_dump($_POST);

$output = "";

if (!check($_POST)) {
	$output .= "<tr><td>Ошибка. Введены неверные значения.</td></tr>";
	$output .= "
		</table>
		</body>
		</html>
		";
}

else {
	$x = $_POST['x'];
	$y = $_POST['y'];
	$r = $_POST['r'];

	$output .= "<tr><td>";

	$table = "<table class=\"inner\"><tr><th>Координата Х</th><th>Координата Y</th><th>Значение R</th><th>Результат</th><th>Время выполнения</th></tr>";
	
	$new = array();
	foreach ($x as $vx) {
		foreach ($r as $vr) {
			$start = microtime(true);
			usleep(100);
			$add = "<tr><td> $vx </td><td> $y </td><td> $vr </td><td>";
			$res = (checkMath($vx, $y, $vr) ? "Да" : "Нет");
			$add .= $res;
			$finish = microtime(true);
			$delta = $finish - $start;
			$add .= "</td><td>$delta</td></tr>";
			$table .= $add;
			$nreq = new Request($vx, $y, $vr, $res, $delta);
			array_push($new, serialize($nreq));
		}
	}
	
	$table .= "<tr><th colspan=5>История запросов</th></tr>";
	foreach (array_reverse($history) as $r) {
		$req = unserialize($r);
		$add = "<tr><td>$req->x</td><td>$req->y</td><td>$req->r</td><td>$req->in</td><td>$req->t</td></tr>";
		$table .= $add;
	}
	$history = array_merge($history, $new);

	$table .= "</table>";

	$output .= $table;
	$output .= "</td><td><img src=\"areas.png\"></td></tr>";
	date_default_timezone_set("UTC");
	$time = time();
	$offset = 3;
	$time += 3 * 3600;
	$_SESSION['history'] = $history;
	$output .= "<tr><td><input type=\"button\" onclick=\"reset()\" value=\"Очистить историю\"></td>";
	$output .= "<td>Дата и время: " . date("d-M-Y H:i", $time) . "</td></tr>";
	$output .= "
		</table>
		</body>
		</html>
		";
}


echo $output;

function check($values) {
	if(empty($values['x']) || (empty($values['y']) && $values['y'] != "0") || empty($values['r'])) return false;
	$x = $values['x'];
	$y = $values['y'];
	$r = $values['r'];
	$valid = true;
	foreach ($x as $val) {
		$valid = ($valid && is_numeric($val) && !is_float($val + 0) && ($val >= -4) && ($val <= 4));
	}
	$valid = ($valid && is_numeric($y) && ($y < 3) && ($y > -3));
	foreach ($r as $val) {
		$valid = ($valid && is_numeric($val) && ($val == 1 || $val == 1.5 || $val == 2 || $val == 2.5 || $val == 3));
	}
	return $valid;
}

function checkMath($x, $y, $r) {
	
	if (($x < -1 * $r) || ($y < -1 * $r) || ($y > $r) || ($x > $r)) return false;
	if (($x > 0) && ($y < 0)) return false;
	if (($x > 0) && ($y > 0)  && (($y > sqrt($r * $r - $x * $x)) || $x > sqrt($r * $r - $y * $y))) return false;
	if (($x < 0) && ($y > 0) && ($y > $x / 2 + $r / 2)) return false;
	return true;
}

?>
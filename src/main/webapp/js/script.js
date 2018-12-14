const CANVAS_COLOR = "rgb(51, 153, 255)";

const X_CHECKBOX_AMOUNT = 9;
const X_MIN_VALUE = -5;

function drawCanvas(canvasElementId, r) {
  let canvas = document.getElementById(canvasElementId),
      context = canvas.getContext("2d");
  // Cleaning
  context.clearRect(0, 0, canvas.width, canvas.height);

  // Rectangle
  context.beginPath();
  context.rect(150, 150, -130, 65);
  context.closePath();
  context.strokeStyle = CANVAS_COLOR;
  context.fillStyle = CANVAS_COLOR;
  context.fill();
  context.stroke();

  // Sector
  context.beginPath();
  context.moveTo(150, 150);
  context.arc(150, 150, 65, -Math.PI / 2, -Math.PI, true);
  context.closePath();
  context.strokeStyle = CANVAS_COLOR;
  context.fillStyle = CANVAS_COLOR;
  context.fill();
  context.stroke();

  // Triangle
  context.beginPath();
  context.moveTo(150, 150);
  context.lineTo(150, 20);
  context.lineTo(270, 150);
  context.lineTo(150, 150);
  context.closePath();
  context.strokeStyle = CANVAS_COLOR;
  context.fillStyle = CANVAS_COLOR;
  context.fill();
  context.stroke();

  // Drawing of axis
  context.beginPath();
  context.font = "10px Verdana";
  context.moveTo(150, 0);
  context.lineTo(150, 300);
  context.moveTo(150, 0);
  context.lineTo(145, 15);
  context.moveTo(150, 0);
  context.lineTo(155, 15);
  context.fillText("Y", 160, 10);
  context.moveTo(0, 150);
  context.lineTo(300, 150);
  context.moveTo(300, 150);
  context.lineTo(285, 145);
  context.moveTo(300, 150);
  context.lineTo(285, 155);
  context.fillText("X", 290, 135);

  context.strokeStyle = "black";
  context.fillStyle = "black";

  // X axis
  context.moveTo(145, 20);
  context.lineTo(155, 20);
  context.fillText(r, 160, 20);
  context.moveTo(145, 85);
  context.lineTo(155, 85);
  context.fillText((r / 2), 160, 78);
  context.moveTo(145, 215);
  context.lineTo(155, 215);
  context.fillText(-(r / 2), 160, 215);
  context.moveTo(145, 280);
  context.lineTo(155, 280);
  context.fillText(-r, 160, 280);

  // Y axis
  context.moveTo(20, 145);
  context.lineTo(20, 155);
  context.fillText(-r, 20, 170);
  context.moveTo(85, 145);
  context.lineTo(85, 155);
  context.fillText(-(r / 2), 70, 170);
  context.moveTo(215, 145);
  context.lineTo(215, 155);
  context.fillText((r / 2), 215, 170);
  context.moveTo(280, 145);
  context.lineTo(280, 155);
  context.fillText(r, 280, 170);

  context.closePath();
  context.stroke();
}

function createCanvas(id, x, y, r) {
  drawCanvas(id, r);
  context.beginPath();
  context.rect(Math.round(150 + ((x / r) * 130)) - 2,
      Math.round(150 - ((y / r) * 130)) - 2, 4, 4);
  context.closePath();
  context.strokeStyle = "red";
  context.fillStyle = "red";
  context.fill();
  context.stroke();

}

function drawPoint(canvasId, x, y, color) {
  let canvas = document.getElementById(canvasId),
      context = canvas.getContext("2d");

  context.beginPath();
  context.rect(x - 2, y - 2, 4, 4);
  context.closePath();
  context.strokeStyle = color;
  context.fillStyle = color;
  context.fill();
  context.stroke();
}

function checkForm(form) {
  let valid = true;
  let message = "<b>Форма содержит следующие ошибки:</b><br>";

  let valueX;
  let valueY = document.data.y.value;
  let valueR = document.data.r.value;

  let atLeastOneChecked = false;
  for (let i = 0; i < X_CHECKBOX_AMOUNT; ++i) {
    if (document.getElementById("x" + i).checked) {
      valueX = X_MIN_VALUE + i;
      atLeastOneChecked = true;
    }
  }

  if (!atLeastOneChecked) {
    message += "Выберите хотя бы одно значение X<br>";
    valid = false;
  }

  if (isNaN(+(valueY))) {
    message += "Значение Y должно быть числом<br>";
    valid = false;
  } else if (valueY.length > 12) {
    message += "Длина строки с Y не должна превышать 12 символов<br>";
    valid = false;
  } else if (parseFloat(valueY) < -5 || parseFloat(valueY) > 5) {
    message += "Y должен принадлежать промежутку [-5; 5]<br>";
    valid = false;
  }

  if (isNaN(+(valueR))) {
    message += "Значение R должно быть числом<br>";
    valid = false;
  } else if (valueR.length > 12) {
    message += "Длина строки с R не должна превышать 12 символов<br>";
    valid = false;
  } else if (parseFloat(valueR) < 1 || parseFloat(valueR) > 4) {
    message += "R должен принадлежать промежутку [1; 4]<br>";
    valid = false;
  }

  if (valid) {
    createCanvas('canvas', valueX, valueY, valueR);
  } else {
    document.getElementById("errors").innerHTML = message;
  }

  return valid;
}

function isInsideOfArea(x, y, r) {
  if (r === "") {
    r = 1;
  }

  if (r < 0) {
    return false;
  }

  let res;
  if (x <= 0 && y <= 0) {
    res = (x >= -r && y >= -r / 2);
  } else if (x < 0) {
    if (Math.abs(x) > r || Math.abs(y) > r) {
      res = false;
    } else {
      res = (Math.sqrt(x * x + y * y) <= r);
    }
  } else {
    res = (y <= -x + r && y >= 0);
  }

  return res;
}

function onClickCanvasHandler(canvasId, r) {
  var elem = document.getElementById(canvasId);
  var br = elem.getBoundingClientRect();
  var left = br.left;
  var top = br.top;

  var event = window.event;
  var x = event.clientX - left;
  var y = event.clientY - top;

  var isInside = false;
  // var isInside = isInsideOfArea((x - 150) / 130, (150 - y) / 130, r);

  var http = new XMLHttpRequest();
  var url = 'areaCheck.jsp';
  var params = 'x=' + x + ' + &y=' + y + '&r=' + r;
  http.open('POST', url, true);

  http.onreadystatechange = function () {//Call a function when the state changes.
    if (http.readyState === 4 && http.status === 200) {
      alert(http.responseText);
      isInside = http.param("success", false);
    }
  };

  http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  http.send(params);

  if (isInside) {
    drawPoint(canvasId, x, y, "green");
  } else {
    drawPoint(canvasId, x, y, "red");
  }
}
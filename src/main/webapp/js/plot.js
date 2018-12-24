//--------------------------------------------------------------------

var form = document.querySelector("#form-data");
var currentR = 1;

function onRadiusInput() {
  let newR = form.r.value;
  alert(newR);
  if (isNaN(newR) || newR === 0) {
    return;
  }

  drawCanvas(newR);
}

function drawCanvas(r) {
  let canvas = document.querySelector("#canvas");
  let context = canvas.getContext("2d");
  //очистка
  context.clearRect(0, 0, canvas.width, canvas.height);

  //прямоугольник
  context.beginPath();
  context.rect(20, 150, 130, 65);
  context.closePath();
  context.strokeStyle = "blue";
  context.fillStyle = "blue";
  context.fill();
  context.stroke();

  // сектор
  context.beginPath();
  context.moveTo(150, 150);
  context.arc(150, 150, 65, -Math.PI / 2, -Math.PI, true);
  context.closePath();
  context.strokeStyle = "blue";
  context.fillStyle = "blue";
  context.fill();
  context.stroke();

  //треугольник
  context.beginPath();
  context.moveTo(150, 150);
  context.lineTo(280, 150);
  context.lineTo(150, 20);
  context.lineTo(150, 150);
  context.closePath();
  context.strokeStyle = "blue";
  context.fillStyle = "blue";
  context.fill();
  context.stroke();

  //отрисовка осей
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

  // деления X
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
  // деления Y
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
  context.strokeStyle = "black";
  context.fillStyle = "black";
  context.stroke();
}

function isArea(x, y, r) {
  /*let points = [];

  let xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.open("POST", "/", true);
  xmlHttpRequest.setRequestHeader('Content-Type',
      'application/x-www-form-urlencoded');
  let body = "x=" + x + "&y=" + y + "&r=" + r + "&redirect=false";
  xmlHttpRequest.send(body);
  xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState !== 4) {
      return;
    }

    if (xmlHttpRequest.status === 200) {
      points = JSON.parse(xmlHttpRequest.responseText).points;
    }
  };

  return points[points.length - 1].check;*/
  x = (x - 150) / 130;
  y = (150 - y) / 130;

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
      res = (Math.sqrt(x * x + y * y) <= r / 2);
    }
  } else {
    res = (y <= -x + r && y >= 0);
  }

  return res;
}

function onCanvasClick() {
  let canvas = document.querySelector("#canvas");
  let rect = canvas.getBoundingClientRect();

  let left = rect.left;
  let top = rect.top;
  // let event = window.event;

  let x = event.clientX - left;
  let y = event.clientY - top;
  let boolArea = isArea(x, y, currentR);

  drawPoint(x, y, boolArea);
}

function drawPoint(x, y, isArea) {
  let canvas = document.querySelector("#canvas");
  let context = canvas.getContext("2d");

  context.beginPath();
  context.rect(x - 2, y - 2, 4, 4);
  context.closePath();
  if (isArea) {
    context.strokeStyle = "green";
    context.fillStyle = "green";
  } else {
    context.strokeStyle = "red";
    context.fillStyle = "red";
  }

  context.fill();
  context.stroke();
}

function getPoints() {
  let points = [];

  let xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.open("POST", "/", true);
  xmlHttpRequest.setRequestHeader('Content-Type',
      'application/x-www-form-urlencoded');
  xmlHttpRequest.send();
  xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState !== 4) {
      return;
    }

    if (xmlHttpRequest.status === 200) {
      points = JSON.parse(sessionStorage.getItem("points")).points;
    }
  };

  return points;
}

/*
var plot = document.querySelector("#plot-svg");
var plotText = document.querySelector("#svg_text_r");

var inputR = document.querySelector("input[name=\"r\"]");
var form = document.querySelector("#data-form");

plot.onclick =
    function (event) {
      let plotRect = plot.getBoundingClientRect();

      let coordX = event.clientX - plotRect.left;
      let coordY = event.clientY - plotRect.top;
      console.log("coordX " + coordX + " coordY: " + coordY);

      let r = document.querySelector("#data-form").r.value;
      if (!(isNaN(r) || r === 0)) {
        x = r * (coordX - 210) / 160;
        y = r * (-(coordY - 190) / 160);
        console.log("X " + x + " Y: " + y);

        let element = document.createElement("circle");
        element.setAttribute("name", "dot");
        element.setAttribute("r", "2");
        element.setAttribute("cx", coordX);
        element.setAttribute("cy", coordY);
        element.setAttribute("fill", isInsideOfArea(x, y, r) ? "green" : "red");

        plot.appendChild(element);
      }
    };
 */
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

function isArea(x, y, r, callback) {
  return checkPoint(x, y, r, callback); // todo
  // return checkPointLocally(x, y, r); // todo
}

// todo
function checkPoint(x, y, r, callback) {
  let xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.open("POST", "?"
      + "x=" + (r * ((x - 150) / 130))
      + "&y=" + (r * ((150 - y) / 130))
      + "&r=" + r
      + "&redirect=false", true);
  xmlHttpRequest.setRequestHeader('Content-Type',
      'application/x-www-form-urlencoded');
  xmlHttpRequest.send();

  xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4 && xmlHttpRequest.status === 200) {
      console.log("Response: " + xmlHttpRequest.responseText);

      let points = JSON.parse(xmlHttpRequest.responseText).points;
      console.log("Points: " + points);

      callback(points[points.length - 1].result);
    } else {
      callback(false);
    }
  };
}

function checkPointLocally(x, y, r) {
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
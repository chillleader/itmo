function resize_area(valueR) {
  var oldR = inputR.value;
  inputR.value = valueR;
  var rOnChart = document.querySelector("text");
  rOnChart.innerHTML = "R = " + valueR;

  var dots = document.querySelectorAll("circle[name='dot']");

  for (var i = 0; i < dots.length; i++) {
    var cx = (dots[i].cx.animVal.value - 190) * oldR / valueR + 190;
    var cy = (dots[i].cy.animVal.value - 210) * oldR / valueR + 210;

    dots[i].setAttribute("cx", cx);
    dots[i].setAttribute("cy", cy);
  }
}

document.getElementById("plot-svg").onclick =
    function (event) {

      var elem = document.querySelector("#plot-svg");
      var br = elem.getBoundingClientRect();

      var coordX = event.clientX - br.left;
      var coordY = event.clientY - br.top;

      console.log("X " + coordX + " Y: " + coordY);

      let r = document.querySelector("#data-form").r.value;
;
      if (!(isNaN(r) || r === 0)) {
        x = r * (coordX - 210) / 160;
        y = r * (-(coordY - 190) / 160);

        console.log("X " + x + " Y: " + y);
        var element = document.createElement("circle");
        element.setAttribute("name", "dot");
        element.setAttribute("r", "2");
        element.setAttribute("cx", coordX);
        element.setAttribute("cy", coordY);
        element.setAttribute("fill", "red");

        document.querySelector("#plot-svg").appendChild(element);
        // document.getElementById("plot-svg").contentWindow.reload(true);

        // alert("X: " + x + " Y: " + y + " R: " + r);
        var xmlHttpRequest = new XMLHttpRequest();

        xmlHttpRequest.open("POST", "/", true);
        xmlHttpRequest.setRequestHeader('Content-Type',
            'application/x-www-form-urlencoded');
        var body = 'x=' + encodeURIComponent(x*r) +
            '&y=' + encodeURIComponent(y*r) +
            '&r=' + encodeURIComponent(r);
        xmlHttpRequest.send(body);

        var points = [];
        xmlHttpRequest.onreadystatechange = function () {
          if (xmlHttpRequest.readyState !== 4) {
            console.log("exception");
            return;
          }

          if (xmlHttpRequest.status != 200) {
            console.log("ura");
            alert(xmlHttpRequest.status + ': ' + xmlHttpRequest.statusText);
          } else {
            console.log("exc 2");
          }
        };
      }
    };

function checkForm(form) {
  let valid = true;
  // let message = "<b>Форма содержит следующие ошибки:</b><br>";
  let message = "<b>There are the following errors in the form:</b><br>";

  let valueX;
  let valueY = document.getElementById("data-form").y.value;
  let valueR = document.getElementById("data-form").r.value;

  let atLeastOneChecked = false;
  for (let i = 0; i < 9; ++i) {
    if (document.getElementById("x" + i).checked) {
      if (atLeastOneChecked) {
        // message += "Необходимо выбрать лишь одно значение X<br>";
        message += "Only one X value should be checked<br>";
        valid = false;
        break;
      }
      valueX = -5 + i;
      atLeastOneChecked = true;
    }
  }

  if (!atLeastOneChecked) {
    // message += "Выберите хотя бы одно значение X<br>";
    message += "At least one X value should be picked<br>";
    valid = false;
  }

  if (isNaN(+(valueY))) {
    // message += "Значение Y должно быть числом<br>";
    message += "Y values should be a number<br>";
    valid = false;
  } else if (valueY.length > 12) {
    // message += "Длина строки с Y не должна превышать 12 символов<br>";
    message += "The length of the Y should not exceed 12 symbols<br>";
    valid = false;
  } else if (parseFloat(valueY) < -5 || parseFloat(valueY) > 5) {
    // message += "Y должен принадлежать промежутку [-5; 5]<br>";
    message += "Y value should be in interval [-5; 5]<br>";
    valid = false;
  }

  if (isNaN(+(valueR))) {
    message += "Значение R должно быть числом<br>";
    valid = false;
  } else if (valueR.length > 12) {
    // message += "Длина строки с R не должна превышать 12 символов<br>";
    message += "The length of the R should not exceed 12 symbols<br>";
    valid = false;
  } else if (parseFloat(valueR) < 1 || parseFloat(valueR) > 4) {
    message += "R value should be in interval [-5; 5]<br>";
    valid = false;
  }

  if (!valid) {
    document.getElementById("errors").innerHTML = message;
  }

  return valid;
}

function isInsideOfArea(x, y, r) {
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
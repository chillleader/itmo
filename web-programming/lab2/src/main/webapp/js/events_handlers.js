var currentR = 1;

function onFormSubmit() {
  let form = document.querySelector("#data-form");

  let valid = true;

  // let message = "<b>Форма содержит следующие ошибки:</b><br>";
  let message = "<b>There are the following errors in the form:</b><br>";

  let valueX;
  let valueY = form.y.value;
  let valueR = form.r.value;

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

function onRadiusInput() {
  let newR = document.querySelector("#data-form").r.value;
  // alert(newR);
  if (isNaN(newR) || newR === 0) {
    return;
  }

  currentR = newR;
  drawCanvas(currentR);
}

function onCanvasClick() {
  var canvas = document.querySelector("#canvas");
  var rect = canvas.getBoundingClientRect();

  var left = rect.left;
  var top = rect.top;
  var event = window.event;

  var x = event.clientX - left;
  var y = event.clientY - top;

  isArea(x, y, currentR, function (insideArea) {
    drawPoint(x, y, insideArea);
  });
}
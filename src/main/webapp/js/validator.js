function checkForm(form) {
  valid = true;
  message = "<b>Форма содержит следующие ошибки:</b><br>";

  if (
      !(
          document.getElementById("x0").checked
          || document.getElementById("x1").checked
          || document.getElementById("x2").checked
          || document.getElementById("x3").checked
          || document.getElementById("x4").checked
          || document.getElementById("x5").checked
          || document.getElementById("x6").checked
          || document.getElementById("x7").checked
          || document.getElementById("x8").checked
      )) {
    message += "Выберите хотя бы одно значение X<br>";
    valid = false;
  }

  if (isNaN(+(document.data.y.value))) {
    message += "Y должен быть числом<br>";
    valid = false;
  } else if (document.data.y.value.length > 17) {
    message += "Длина строки с Y не должна превышать 17 символов<br>";
    valid = false;
  } else if (parseFloat(document.data.y.value) >= 3 || parseFloat(
      document.data.y.value) <= -3) {
    message += "Y должен принадлежать промежутку (-3; 3)<br>";
    valid = false;
  }

  if (!(
      document.getElementById("y0").checked
      || document.getElementById("y1").checked
      || document.getElementById("y2").checked
      || document.getElementById("y3").checked
      || document.getElementById("y4").checked
  )) {
    message += "Выберите хотя бы одно значение R<br>";
    valid = false;
  }

  if (!valid) {
    document.getElementById("errors").innerHTML = message;
  }

  return valid;
}

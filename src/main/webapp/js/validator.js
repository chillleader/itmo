const X_CHECKBOX_AMOUNT = 9;

function checkForm(form) {
    let valid = true;
    let message = "<b>Форма содержит следующие ошибки:</b><br>";

    let valueY = document.data.y.value;
    let valueR = document.data.r.value;

    let atLeastOneChecked = false;
    for (i = 0; i < X_CHECKBOX_AMOUNT; ++i) {
        if (document.getElementById("x" + i).checked) {
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
    } else if (parseFloat(valueY) <= -5 || parseFloat(
        valueY) >= 5) {
        message += "Y должен принадлежать промежутку [-5; 5]<br>";
        valid = false;
    }

    if (isNaN(+(valueR))) {
        message += "Значение R должно быть числом<br>";
        valid = false;
    } else if (valueR.length > 12) {
        message += "Длина строки с R не должна превышать 12 символов<br>";
        valid = false;
    } else if (parseFloat(valueR) >= 1 || parseFloat(valueR) <= 4) {
        message += "R должен принадлежать промежутку [1; 4]<br>";
        valid = false;
    }

    if (!valid) {
        document.getElementById("errors").innerHTML = message;
    }

    return valid;
}

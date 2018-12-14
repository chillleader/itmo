const CANVAS_COLOR = "rgb(51, 153, 255)";

function drawCanvas(canvasElementId, r) {
    let canvas = document.getElementById(canvasElementId),
        context = canvas.getContext("2d");
    // Cleaning
    context.clearRect(0, 0, canvas.width, canvas.height);

    // Rectangle
    context.beginPath();
    context.rect(150, 150, 130, -65);
    context.closePath();
    context.strokeStyle = CANVAS_COLOR;
    context.fillStyle = CANVAS_COLOR;
    context.fill();
    context.stroke();

    // Sector
    context.beginPath();
    context.moveTo(150, 150);
    context.arc(150, 150, 130, -Math.PI / 2, -Math.PI, true);
    context.closePath();
    context.strokeStyle = CANVAS_COLOR;
    context.fillStyle = CANVAS_COLOR;
    context.fill();
    context.stroke();

    // Triangle
    context.beginPath();
    context.moveTo(85, 150);
    context.lineTo(150, 280);
    context.lineTo(150, 150);
    context.lineTo(85, 150);
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
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();
}

function createCanvas(id, x, y, r) {
    drawCanvas(id, r);
    context.beginPath();
    context.rect(Math.round(150 + ((x / r) * 130)) - 2, Math.round(150 - ((y / r) * 130)) - 2, 4, 4);
    context.closePath();
    context.strokeStyle = "red";
    context.fillStyle = "red";
    context.fill();
    context.stroke();

}

function onClickCanvasEvent(canvasId, R) {
    var elem = document.getElementById(canvasId);
    var br = elem.getBoundingClientRect();
    var left = br.left;
    var top = br.top;
    var event = window.event;
    var x = event.clientX - left;
    var y = event.clientY - top;
    var boolArea = isInsideOfArea(x, y, R);
    drawPoint(canvasId, x, y, boolArea);
}

function isInsideOfArea(x, y, R) {
    x = (x - 150) / 130;
    y = (150 - y) / 130;

    if (x <= 0 && y <= 0 && x * x + y * y <= R * R) {
        return 'true';
    }

    if (x <= 0 && y >= 0 && y <= (2.0 * x + 1.0 * R)) {
        return 'true';
    }

    if (x >= 0 && y <= 0 && x <= R && y >= -R) {
        return 'true';
    }

    return 'false';
}

function drawPoint(id, x, y, isArea) {
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");

    context.beginPath();
    context.rect(x - 2, y - 2, 4, 4);
    context.closePath();
    if (isArea === 'true') {
        context.strokeStyle = "green";
        context.fillStyle = "green";
    } else {
        context.strokeStyle = "red";
        context.fillStyle = "red";
    }
    context.fill();
    context.stroke();

}
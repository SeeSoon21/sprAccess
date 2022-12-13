ws = new WebSocket("ws://localhost:8080/record")

ws.onopen = function() {
    console.log("соединение установлено: " + window.location.href)
    //string[2] -- название класса
    let string = window.location.pathname.split('/');
    console.log("string: " + string);
    console.log("class: " + string[2]);
    let json = {
        className : string[2],
        id : string[3]
    };

    ws.send(JSON.stringify(json));
}

ws.onmessage = function () {

}

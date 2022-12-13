ws = new WebSocket("ws://localhost:8080/record")

ws.onopen = function() {
    console.log("соединение установлено: " + window.location.href)
    //string[2] -- название класса
    let string = window.location.pathname.split('/');
    let complete_string = string[2] + ":" + string[3];

    /*let json = {
        className : string[2],
        id : string[3]
    };*/

    ws.send(complete_string);
}

ws.onmessage = function () {

}

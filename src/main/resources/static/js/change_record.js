ws_change = new WebSocket("ws://localhost:8080/change")

ws_change.onopen = function() {
    console.log("connection(change_record.js)");
}

function formSubmitData() {
    let record_array= Array.from(document.querySelectorAll('input'))
        .reduce((acc, input) =>
            ({...acc, [input.id]: input.value }), {});

    console.log("form-data: ")
    let str = "";
    for (let key in record_array) {
        console.log(key + ": " + record_array[key]);
        str += record_array[key] + ':';
    }

    ws_change.send(str);
    //ws.close();
}
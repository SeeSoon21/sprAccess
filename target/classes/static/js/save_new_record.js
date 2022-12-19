let ws_sock = new WebSocket("ws://localhost:8080/save");

function save_new_record() {
    //string[2] -- название класса
    let complete_string = table_name + ":";
    console.log("complete_string: " + complete_string);

    let record_array = Array.from(document.querySelectorAll('input'))
        .reduce((acc, input) =>
            ({...acc, [input.id]: input.value}), {});

    console.log("form-data: ")
    let str = complete_string;
    for (let key in record_array) {
        console.log(key + ": " + record_array[key]);
        str += record_array[key] + ':';
    }

    ws_sock.send(str);
}
ws_change = new WebSocket("ws://localhost:8080/change")

ws_change.onopen = function () {
    console.log("connection(change_record.js)");
}

function formSubmitData() {
    //string[2] -- название класса
    let string = window.location.pathname.split('/');
    let complete_string = string[2] + ":";

    let record_array = Array.from(document.querySelectorAll('input'))
        .reduce((acc, input) =>
            ({...acc, [input.id]: input.value}), {});

    console.log("form-data: ")
    let str = complete_string;
    for (let key in record_array) {
        console.log(key + ": " + record_array[key]);
        str += record_array[key] + ':';
    }

    //if (validation_function()) {
        ws_change.send(str);
    //}

    //ws.close();
}


/*
function validation_function(record_array) {
    let without_error = true;

    for (let key in record_array) {
        console.log("ключ для проверки: " + key);
        if (key.includes("id")) {
            console.log("ключ включает id");
            if (!/^\d+$/.test(record_array[key])) {
                console.log("не прошёл проверку");
                document.getElementById(key).setCustomValidity("Неверно указан id!");
                without_error = false;
            }
        }
        if (key.includes("date") || key.includes("birthday")) {
            if (! /(\d{4}|\d{2})(0?[1-9]|1[0-2])(0?[1-9]|[12]\d|30|31)/.test(record_array[key])) {
                document.getElementById(key).setCustomValidity("Неверно указана дата!");
                without_error = false;
            }
        }

        if (key.includes("name") || key.includes("surname") || key.includes("patronymic") ||
            key.includes("address") || key.includes("gender") || key.includes("marital_status")) {
            if (! /[a-zA-Z]/.test(key)) {
                document.getElementById(key).setCustomValidity("Неверно указана строка!");
                without_error = false;
            }
        }

        if (key === "price" || key === "quantity" || key === "expiration_date") {
            if (! /^\d+$/.test(key)) {
                document.getElementById(key).setCustomValidity("Введите численное значение!")
                without_error = false;
            }
        }
    }

    return without_error;
}*/
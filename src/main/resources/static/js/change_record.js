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
    console.log("record_array data: " + record_array.data);


    let str = complete_string;
    for (let key in record_array) {
        console.log(key + ": " + record_array[key]);
        str += record_array[key] + ':';
    }

    if (validation_function(record_array)) {
        ws_change.send(str);
    }

    //ws.close();
}

//сохраняем измененную запись
function save_btn() {
    let div = document.createElement("div");
    let save_btn = document.createElement("input");

    save_btn.type = "button";
    save_btn.className = "btn btn-primary ml-3";
    save_btn.id = "saveChangesBtn";
    save_btn.value = "Сохранить"

    div.appendChild(save_btn);
    document.getElementById("fields-form").appendChild(save_btn);


    save_btn.addEventListener("click", function () {
        console.log("Сохранили");
        formSubmitData();
    });
}


function validation_function(record_array) {
    let without_error = true;
    console.log('валидация момент: ' + record_array);

    for (let key in record_array) {
        console.log("ключ для проверки: " + key);
        if (key.includes("id")) {
            console.log("ключ включает id");
            let errorParagraph = document.createElement("p");
            errorParagraph.id = "errorParagraphId";

            if (!/^\d+$/.test(record_array[key])) {
                console.log("не прошёл проверку");
                document.getElementById(key).setCustomValidity("Неверно указан id!");

                errorParagraph.appendChild(document.createTextNode("Неверно указан id!"));
                errorParagraph.className = "alert alert-danger";
                document.getElementById("fields-form").appendChild(errorParagraph);

                without_error = false;
            } else {
                if (document.getElementById("errorParagraphId") != null) {
                    document.getElementById("errorParagraphId").textContent = "";
                    document.getElementById("errorParagraphId").className = "";
                }
            }
        }
        if (key.includes("date") || key.includes("birthday")) {
            console.log("ключ включает date");
            let errorParagraph = document.createElement("p");
            errorParagraph.id = "errorParagraphDate";

            //if (! /(\d{4}|\d{2})(0?[1-9]|1[0-2])(0?[1-9]|[12]\d|30|31)/.test(record_array[key])) {
            if (! /[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]/.test(record_array[key])) {
                console.log("не прошёл проверку");
                //document.getElementById(key).setCustomValidity("Неверно указана дата!");
                document.getElementById(key).setCustomValidity("Неправильно указана дата!");

                errorParagraph.appendChild(document.createTextNode("Неправильно указана дата!"));
                errorParagraph.className = "alert alert-danger";
                document.getElementById("fields-form").appendChild(errorParagraph);

                without_error = false;
            }
            else {
                if (document.getElementById("errorParagraphDate") != null) {
                    document.getElementById("errorParagraphDate").textContent = "";
                    document.getElementById("errorParagraphDate").className = "";
                }
            }
        }

        if (key.includes("name") || key.includes("surname") || key.includes("patronymic") ||
            key.includes("address") || key.includes("gender") || key.includes("marital_status")) {
            console.log("ключ включает символы имён");
            if (! /^([a-zA-Z]|\s)*$/.test(key)) {
                console.log("Ключ не прошёл проверку");
                document.getElementById(key).setCustomValidity("Неверно указана строка!");
                document.getElementById(key).pattern = "[a-zA-Z]";

                let errorLabel = document.createElement("p")
                errorLabel.appendChild(document.createTextNode("Неверно указана строка!"));
                errorLabel.className = "alert alert-danger";
                document.getElementById("fields-form").appendChild(errorLabel);

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
}
let ws = new WebSocket("ws://localhost:8080/record")
let className;
let id;

ws.onopen = function() {
    //string[2] -- название класса
    let string = window.location.pathname.split('/');

    className = string[2];
    id = string[3];

    let complete_string = className + ":" + id;

    ws.send(complete_string);
}

ws.onmessage = function (ev) {
    console.log("data: " + ev.data);
    parseJson(ev.data);
}

function parseJson(json) {
    let jsonData = JSON.parse(json);
    let fields_form = document.getElementById("fields-form");
    let field_container;
    let label_field;
    let input_text;

    //создаем для каждого поля и значения из бд -- свою форму, отображаемую на HTML
    //остается сделать кнопку сохранения изменений(update) и послать на сервер.(можно postMapping'ом.)
    //либо в функции вебСокета с последующим оповещением о сохранении
    for (let key in jsonData) {
        console.log(key + ":" + jsonData[key]);

        field_container = document.createElement("div")
        label_field = document.createElement("label");
        input_text = document.createElement("input")

        field_container.className = "form-outline p-3";
        label_field.className = "form-label ml-3 mr-3"
        input_text.className = "form-control";

        label_field.appendChild(document.createTextNode(key));
        input_text.appendChild(document.createTextNode(jsonData[key]));
        input_text.value = jsonData[key];
        input_text.id = key;

        field_container.appendChild(label_field);
        field_container.appendChild(input_text);

        fields_form.appendChild(field_container);
    }


    deleteRecordBtn(className, id);
    save_close_session();
}

function deleteRecordBtn(className, id) {
    let ws_sock = new WebSocket("ws://localhost:8080/delete")

    let deleteBtn;
    let fields_form = document.getElementById("fields-form");

    deleteBtn = document.createElement("input");
    deleteBtn.type = "button";
    deleteBtn.className = "btn btn-danger";
    deleteBtn.id = "delete_btn";
    deleteBtn.value = "Удалить";

    fields_form.appendChild(deleteBtn);

    document.getElementById(deleteBtn.id).onclick = function () {
        //нужно сделать ещё один обработчик
        let json = {
            class_name: className,
            id: id
        }

        ws_sock.send(JSON.stringify(json));
        deleteAllChildElements("fields-form");
    };

}

function deleteAllChildElements(id) {
    const parentNode = document.getElementById(id);
    parentNode.textContent = '';

    let label = document.createElement("h2");
    label.appendChild(document.createTextNode("Запись успешно удалена!"));
    parentNode.appendChild(label);

    let backBtn = document.getElementById("saveChangesBtn");
    backBtn.value = "Вернуться";
    backBtn.onclick = function () {
        location.href = "http://localhost:8080/db";
    }
}


function save_close_session() {
    document.getElementById("saveChangesBtn").onclick(function () {
        ws.close();
    });
}





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
    //document.getElementById("fields-form").textContent = "";
    parseJson(ev.data);

    save_btn();
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

    //делаем поле id неизменяемым, но видимым
    document.getElementById("id").readOnly = true;

    deleteRecordBtn(className, id);
    deleteByButton();
    //save_close_session();
}

function deleteRecordBtn(className, id) {
    let ws_sock = new WebSocket("ws://localhost:8080/delete")

    let deleteBtn;
    let fields_form = document.getElementById("fields-form");

    deleteBtn = document.createElement("input");
    deleteBtn.type = "button";
    deleteBtn.className = "btn btn-danger ml-3";
    deleteBtn.id = "delete_btn";
    deleteBtn.value = "Удалить";

    fields_form.appendChild(deleteBtn);

    deleteBtn.addEventListener("click", function () {
        let json = {
            class_name: className,
            id: id
        }

        ws_sock.send(JSON.stringify(json));
        deleteAllChildElements("fields-form");
    });

}
function deleteByButton() {
    let ws_del = new WebSocket("ws://localhost:8080/delete")
    //ws_del.send();

    //общий див, в который будем складывать все новые элементы
    let deleteDiv = document.createElement("div");
    //форма, в которую положим див
    let fieldsForm = document.getElementById("fields-form");

    //поле для удаления, которое должен ввести пользователь
    let deleteFieldName = document.createElement("input");
    deleteFieldName.type = "text";
    deleteFieldName.className = "btn btn-primary ml-3";
    deleteFieldName.id = "deleteByFieldName";
    deleteFieldName.placeholder = "Введите название поля";
    deleteFieldName.appendChild(document.createTextNode("Удалить запись по полю"))

    //
    let deleteFieldValue = document.createElement("input");
    deleteFieldValue.type = "text";
    deleteFieldValue.id = "deleteByFieldValue";
    deleteFieldValue.placeholder = "Значение удаляемого поля"

    let deleteByFieldButton = document.createElement("input");
    deleteByFieldButton.type = "button";
    deleteByFieldButton.id = "deleteByFieldButton";
    deleteByFieldButton.value = "Удалить";

    deleteDiv.appendChild(deleteFieldName);
    deleteDiv.appendChild(deleteFieldValue);
    deleteDiv.appendChild(deleteByFieldButton);

    fieldsForm.appendChild(deleteDiv);

    deleteByFieldButton.addEventListener("click", function () {
        //тут нужно будет пройтись по всем полям, id которых содержат "deleteByField"
        console.log("click");
        let inputIdArray = document.querySelectorAll("input[type='text']");

        let field_values = [];
        for (let i=0; i < inputIdArray.length; i++) {
            console.log(inputIdArray[i].value);
            field_values[i] = inputIdArray[i].value;
        }

        let json = {
            className: className,
            fieldsName: field_values[0],
            fieldValue: field_values[1]
        }

        ws_del.send(JSON.stringify(json));
        deleteAllChildElements("fields-form");
    })
}

//удаление всех дочерних узлов(полей инпутов) и создание кнопки возращения
function deleteAllChildElements(id) {
    console.log("Удаление дочерних элементов");
    const parentNode = document.getElementById(id);
    parentNode.textContent = '';

    let label = document.createElement("h2");
    label.appendChild(document.createTextNode("Запись успешно удалена!"));
    parentNode.appendChild(label);

    let backBtn = document.createElement("input");
    backBtn.type = "button";
    backBtn.value = "Вернуться";
    backBtn.className = "";
    backBtn.onclick = function () {
        location.href = "http://localhost:8080/db";
    }

    parentNode.appendChild(backBtn);
}

//создание кнопки сохранения
function save_btn() {
    let div = document.createElement("div");
    let save_btn = document.createElement("input");

    save_btn.type = "button";
    save_btn.className = "btn btn-primary ml-3";
    save_btn.id = "saveChangesBtn";
    save_btn.value = "Сохранить"

    div.appendChild(save_btn);
    document.getElementById("fields-form").appendChild(save_btn);
}

function save_close_session() {
    document.getElementById("saveChangesBtn").onclick(function () {
        ws.close();
    });
}





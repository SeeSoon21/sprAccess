let ws = new WebSocket("ws://localhost:8080/insert")
let table_name;

ws.onopen = function () {
    console.log("connection установлено");
}

ws.onmessage = function (json) {
    console.log("сообщение пришло");
    document.getElementById("fields-form").textContent = "";
    parse_inp_json(json);


    //Вот тут беда.
    save_btn();
}

function parse_inp_json(fields_array_string) {
    let fields_array = fields_array_string.data.split(", ");

    ///let fields_array = fields_array_string.data.split(", ");
    //console.log('fields length: ' + fields_array.data.length);

    let fields_form = document.getElementById("fields-form");
    let field_container;
    let label_field;
    let input_text;

    for (let i = 0; i < fields_array.length; i++) {
        console.log(fields_array[i]);

        field_container = document.createElement("div")
        label_field = document.createElement("label");
        input_text = document.createElement("input")

        field_container.className = "form-outline p-3";
        label_field.className = "form-label ml-3 mr-3"
        input_text.className = "form-control";
        input_text.type = "text";

        label_field.appendChild(document.createTextNode(fields_array[i]));
        input_text.id = fields_array[i];

        field_container.appendChild(label_field);
        field_container.appendChild(input_text);

        fields_form.appendChild(field_container);

    }
}

function handle_current_table(btn) {
    table_name = btn.value;
    ws.send(table_name);
}

function save_btn() {
    let div = document.createElement("div");
    let save_btn = document.createElement("input");

    save_btn.type = "button";
    save_btn.className = "btn btn-primary ml-3";
    save_btn.id = "saveChangesBtn";
    save_btn.value = "Сохранить"

    div.appendChild(save_btn);
    document.getElementById("fields-form").appendChild(save_btn);

    ifSaveBtnExists();
}

//////////////////////////////////////////////////
let ws_sock = new WebSocket("ws://localhost:8080/save");

function ifSaveBtnExists() {
    if (document.getElementById("saveChangesBtn") != null) {
        let saveButton = document.getElementById("saveChangesBtn");
        saveButton.addEventListener("click", function save_new_record() {
            //string[2] -- название класса
            let complete_string = table_name + ":";
            console.log("complete_string: " + complete_string);

            //собираем данные со всех input'ов
            let record_array = Array.from(document.querySelectorAll('input[type="text"]'))
              .reduce((acc, input) =>
                   ({...acc, [input.id]: input.value}), {});



            console.log("form-data: ")
            let str = complete_string;
            for (let key in record_array) {
                console.log(key + ": " + record_array[key]);
                str += record_array[key] + ':';
            }

            ws_sock.send(str);
            deleteAllChildElements("fields-form");
        });
    }
}


//удаляем все input-text поля и убираем кнопку сохранения при нажатии
function deleteAllChildElements(id) {
    const parentNode = document.getElementById(id);
    let label = document.createElement("h2");

    parentNode.textContent = '';

    label.appendChild(document.createTextNode("Запись успешно добавлена!"));
    parentNode.appendChild(label);

    //this.parentNode.removeChild(saveBtn);

    backButton();

}

//создание кнопки возврата
function backButton() {
    let parentNode = document.getElementById("fields-form");
    let backBtn = document.createElement("input");
    backBtn.id = "backBtn";
    backBtn.type = "button";
    backBtn.value = "Вернуться";
    parentNode.appendChild(backBtn);
    backBtn.href = "http://localhost:8080/db";

    backBtn.onclick = function () {
        location.href = "http://localhost:8080/db";
    }

}

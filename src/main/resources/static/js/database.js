let ws = new WebSocket("ws://localhost:8080/db_action")
let btnClassName;

ws.onopen = function () {
    console.log("connection established");
}

ws.onmessage = function (ev) {
    let message = ev.data;
    let jsonData = JSON.parse(message);
    //document.getElementById("all_tables_data_div_child").textContent = "";
    document.getElementById("card-table").textContent = "";
    document.getElementById("medium_panel").textContent = "";


    createRecord(jsonData, btnClassName);
    selectManagerButtons(jsonData);
    requestWindow();
}

//каждый запись добавляется в соответсвующий список
function createRecord(jsonData, className) {
    //главная таблица, содержащая все записи
    let card_table = document.getElementById("card-table");
    let local_card; //запись
    let body_card; //текст в каждой отдельной записи
    let a_href;

    for (let i = 0; i < jsonData.length; i++) {
        local_card = document.createElement('div');
        body_card = document.createElement('div');
        a_href = document.createElement("a");

        a_href.setAttribute("id", "edit");

        a_href.textContent = "Edit";
        local_card.className = "card text-white bg-dark mb-3";
        body_card.className = "card-body";

        for (let key in jsonData[i]) {
            console.log(key + ":" + jsonData[i][key]);

            let elem = document.createElement('span');

            elem.className = "card-text";

            elem.appendChild(document.createTextNode(key + ": " + jsonData[i][key]));
            elem.appendChild(document.createElement("br"))
            body_card.appendChild(elem);

        }
        a_href.href = "http://localhost:8080/record/" + className + '/' + jsonData[i]["id"];


        //local_card.appendChild(document.createTextNode("<br /><br />"))
        body_card.appendChild(a_href);
        local_card.appendChild(body_card);
        card_table.appendChild(local_card);

    }
}

function handle_button(btn) {
    let message = btn.value;
    btnClassName = message;
    console.log("message(to server): " + message);

    ws.send(message);
}

/**
 * функция, создающая кнопки управления(select по дискретным полям)
 * вывод только имен или возраста, например
 */
let ws_select_by_button = new WebSocket("ws://localhost:8080/select_by_button");


function selectManagerButtons(jsonData) {

    //получаем форму, к которой будем "приклеивать" кнопки(прикрепляем к навбару, по сути)
    let all_tables_data_div_child = document.createElement("div");
    all_tables_data_div_child.id = "all_tables_data_div_child";

    let commonDiv = document.createElement("div");
    commonDiv.id = "checkboxButtons";
    //commonDiv.className = "form-check form-switch";

    // создаем надпись перед кнопками
    let paragraph = document.createElement("p");
    paragraph.className = "ml-3";
    paragraph.appendChild(document.createTextNode("Вывести только: "));
    commonDiv.appendChild(paragraph);

    checkboxSubmitEvent();

    //получим строку, содержащую все имеющиеся имена полей
    let fieldNameString = jsonData[0];
    //перебираем все ключи(названия полей), пришешие с БД и создаем соответствующие кнопки
    for (let key in fieldNameString) {
        let checkBoxDiv = document.createElement("div");
        checkBoxDiv.className = "form-check form-check-inline ml-3";

        //создаем checkBox для каждого поля
        let checkbox = document.createElement("input");
        checkbox.className = "form-check-input ml-2";
        checkbox.type = "checkbox";
        checkbox.id = key + "_checkbox";

        let field_name = document.createElement("label");
        field_name.appendChild(document.createTextNode(key));
        field_name.for = checkbox.id;
        field_name.className = "form-check-label";

        checkBoxDiv.appendChild(field_name);
        checkBoxDiv.appendChild(checkbox);

        commonDiv.appendChild(checkBoxDiv);
        //commonDiv.appendChild(checkboxSubmit);
    }

    all_tables_data_div_child.appendChild(commonDiv);
    document.getElementById("medium_panel").appendChild(all_tables_data_div_child);
    //all_tables_data.appendChild(all_tables_data_div_child);


}

//по нажатию на клавишу "отправить", формирует столбцы, соответсвующие запросу
function checkboxSubmitEvent() {
    //упаковываем данные
    let checkboxJson = {};

    let commonDiv = document.getElementById("checkboxButtons");

    let checkboxSubmit = document.createElement("input");
    checkboxSubmit.type = "button";
    checkboxSubmit.id = "checkboxSubmit";
    checkboxSubmit.value = "Показать";
    checkboxSubmit.addEventListener("click", function () {
        document.getElementById("card-table").textContent = "";
        console.log("event");
        let resultSet = document.querySelectorAll("input[type='checkbox']");

        checkboxJson["className"] = btnClassName;
        for (let i = 0; i < resultSet.length; i++) {
            if (resultSet[i].checked) {
                let element = resultSet[i].parentElement.textContent.trim();
                console.log("checkbox: " + element);
                checkboxJson[resultSet[i].id] = element;
            }
        }
        commonDiv.appendChild(checkboxSubmit);
        ws_select_by_button.send(JSON.stringify(checkboxJson));
    });
}


ws_select_by_button.onmessage = function answerByServerSelectByValues(ev) {
    let data = ev.data;
    let jsonData = JSON.parse(data);
    //очищаем экран
    document.getElementById("card-table").textContent = "";


    for (let key in jsonData) {
        console.log("DATA. key: " + key + ", value:" + jsonData[key]);
    }

    let card_table = document.getElementById("card-table");
    let local_card; //запись
    let body_card; //текст в каждой отдельной записи

    for (let i = 0; i < jsonData.length; i++) {
        local_card = document.createElement('div');
        body_card = document.createElement('div');

        local_card.className = "card text-white bg-dark mb-3";
        body_card.className = "card-body";

        for (let key in jsonData[i]) {
            console.log(key + ":" + jsonData[i][key]);

            let elem = document.createElement('span');

            elem.className = "card-text";

            elem.appendChild(document.createTextNode(key + ": " + jsonData[i][key]));
            elem.appendChild(document.createElement("br"))
            body_card.appendChild(elem);

        }

        //local_card.appendChild(document.createTextNode("<br /><br />"))
        local_card.appendChild(body_card);
        card_table.appendChild(local_card);
    }
}

/**
 * Функция, являющая окно ввода для запросов пользователей
 */
function requestWindow() {
    let card_table = document.getElementById("card-table");
    let commonDiv = document.createElement("div");

    let divExample = document.createElement("div");
    let labelExample = document.createElement("label");
    let inputExample = document.createElement("input");

    let inputDiv = document.createElement("div");
    let inputLabelDiv = document.createElement("label");
    let inputDivWindow = document.createElement("input");

    let button = document.createElement("input");

    commonDiv.className = "form-inline";

    divExample.className = "form-group mb-2";
    labelExample.className = "sr-only";
    inputExample.className = "form-control-plaintext";
    inputExample.type = "text";

    divExample.appendChild(labelExample);
    divExample.appendChild(inputExample);

    inputDiv.class = "form-group mx-sm-3 mb-2";
    inputDiv.id = "inputDiv";
    inputLabelDiv.htmlFor = "inputDiv";
    inputLabelDiv.className = "form-control";
    inputLabelDiv.appendChild(document.createTextNode("\"id > 30\""));
    inputDivWindow.className = "form-control";
    inputDivWindow.type = "text";
    inputDivWindow.id = "input_window"

    inputDiv.appendChild(inputLabelDiv);
    inputDiv.appendChild(inputDivWindow);

    button.className = "btn btn-primary mb-2";
    button.type = "input";
    button.value = "Найти";
    button.id = "queryButton";

    commonDiv.appendChild(divExample);
    commonDiv.appendChild(inputDiv);
    commonDiv.appendChild(button);

    card_table.appendChild(commonDiv);
    queryButtonClick();
}

let ws_query = new WebSocket("ws://localhost:8080/user_query");
function queryButtonClick() {
    document.getElementById("queryButton").addEventListener("click", function () {
        let queryInput = document.getElementById("input_window").value;
        let json = {
            "className" : btnClassName,
            "userQuery" : queryInput
        };
        console.log("inputQ_json: " + json);
        if (queryInput != null) {
            ws_query.send(JSON.stringify(json));
        }

        document.getElementById("input_window").value = "";
    });
}

ws_query.onmessage = function(ev) {
    let message = ev.data;
    let jsonData = JSON.parse(message);
    //document.getElementById("all_tables_data_div_child").textContent = "";
    document.getElementById("card-table").textContent = "";
    console.log("check");
    document.getElementById("medium_panel").textContent = "";

    createRecord(jsonData, btnClassName);
    selectManagerButtons(jsonData);
    requestWindow();
}







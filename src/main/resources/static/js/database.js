ws=new WebSocket("ws://localhost:8080/db_action")
let btnClassName;

ws.onopen = function () {
    console.log("connection established");
}

ws.onmessage = function (ev) {
    let message = ev.data;
    let jsonData = JSON.parse(message);
    document.getElementById("card-table").textContent = "";

    createRecord(jsonData, btnClassName);
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

        a_href.setAttribute("id","edit");

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

    //document.getElementById("edit").textContent = "edit";
}

function handle_button(btn) {
    let message = btn.value;
    btnClassName = message;
    console.log("message(to server): " + message);

    ws.send(message);
}


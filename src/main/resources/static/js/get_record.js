ws = new WebSocket("ws://localhost:8080/record")

ws.onopen = function() {
    console.log("соединение установлено(get_record): " + window.location.href)
    //string[2] -- название класса
    let string = window.location.pathname.split('/');
    let complete_string = string[2] + ":" + string[3];
    console.log("complete_string: " + complete_string);

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

    document.getElementById("saveChangesBtn").onclick(function () {
        ws.close();
    });
}






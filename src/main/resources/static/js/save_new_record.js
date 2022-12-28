/*let ws_sock = new WebSocket("ws://localhost:8080/save");

function ifSaveBtnExists() {
    if (document.getElementById("saveChangesBtn") != null) {
        let saveButton = document.getElementById("saveChangesBtn");
        saveButton.addEventListener("click", function save_new_record() {
            //string[2] -- название класса
            let complete_string = table_name + ":";
            console.log("complete_string: " + complete_string);

            //собираем данные со всех input'ов
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
            deleteAllChildElements("fields-form");
        });
    }
}


//удаляем все input-text поля и убираем кнопку сохранения при нажатии
function deleteAllChildElements(id) {
    let saveBtn = document.getElementById("saveChangesBtn");

    //saveBtn.onclick = function () {
    saveBtn.addEventListener("click", function () {
        const parentNode = document.getElementById(id);
        parentNode.textContent = '';

        let label = document.createElement("h2");
        label.appendChild(document.createTextNode("Запись успешно добавлена!"));
        parentNode.appendChild(label);

        this.parentNode.removeChild(saveBtn);

        backButton();
    });

    //}
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
*/
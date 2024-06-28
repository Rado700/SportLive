document.addEventListener('DOMContentLoaded', function () {
    const url = "/api/organisation/";

    function setOrganisation(data) {
        const getAllOrganisation = document.getElementById("organisationType");
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id;
            option.textContent = item.name;
            getAllOrganisation.appendChild(option);
        })
    }

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Неверная организация")
            }
            return response.json();
        })
        .then(data => setOrganisation(data))
        .catch(error => console.error(error));
});

const getAllOrganisation = document.getElementById("organisationType");


getAllOrganisation.addEventListener('change', function () {
    const url = "/api/organisation/sport/" + getAllOrganisation.value // пишем url адрес ручки и добавляем id организаций
    function setSport(data) { // создаем шаблонную функцию для setSport
        const getAllSportForOrganisation = document.getElementById("sportType") // находим и добавляем тренеров в переменную
        getAllSportForOrganisation.innerHTML = "<option value=\"sport\"  disabled selected hidden>Спорт</option>";
        data.forEach(item => {  // создаем цикл и пробегаемся по данным (data)
                const option = document.createElement("option"); //создаем переменную option куда складываем все значения
                option.value = item.id;
                option.textContent = item.name_sport;
                getAllSportForOrganisation.appendChild(option); //добавляем все в основную переменную которая будет все отображать
            }
        )
    }

    fetch(url) //открываем url
        .then(response => {
            if (!response.ok) {
                throw new Error("нету такого вида спорта")
            }
            return response.json();
        })
        .then(data => setSport(data)) //выводим всю информацию
        .catch(error => console.error());
})

let getAllSportForOrganisation = document.getElementById("sportType")

getAllSportForOrganisation.addEventListener('change', function () {
    getAllSportForOrganisation = document.getElementById("sportType")
    const url = "/api/couch/sport/organisation/" + getAllSportForOrganisation.value + "/" + getAllOrganisation.value;

    function setCouch(data) {
        const getAllCouch = document.getElementById("couchType");
        getAllCouch.innerHTML = "<option value=\"couch\"  disabled selected hidden>Тренер</option>";
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id;
            option.textContent = item.name;
            getAllCouch.appendChild(option);
        })
    }

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Неверная организация")
            }
            return response.json();
        })
        .then(data => setCouch(data))
        .catch(error => console.error(error));
});

const getAllCouch = document.getElementById("couchType");

function skip() {
    window.location.href = '/account';
}

function next() {
    const couchId = getAllCouch.value;
    const sportId = getAllSportForOrganisation.value;
    const organisationId = getAllOrganisation.value;


    const url = `/api/sport-section/sport/organisation/${sportId}/${organisationId}`;
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Неверная организация")
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            const sportSectionId = data.id;
            fetch("/api/sport-section/user/" + sportSectionId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Нет такого тренера")
                    }
                    return response;
                })
        })
        .catch(error => console.error(error));

    fetch("/api/user/couch/" + couchId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Нет такого тренера")
            }
            return response;
        })
    window.location.href = '/account';
}


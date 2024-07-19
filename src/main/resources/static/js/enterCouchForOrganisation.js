let organisations = false;
let sports = false;

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


function skip() {
    window.location.href = '/couches';
}

function closeConfirmationModal(){
    const hidden = document.getElementById("confirmationModal")
    $("#confirmationModal").modal('hide');
}

function next() {
    const getAllSportForOrganisation = document.getElementById("sportType")
    const getAllOrganisation = document.getElementById("organisationType");
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
                fetch("/api/sport-section/couch/" + sportSectionId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Нет такого тренера")
                        }
                        organisations = true;
                        return response;

                    })
            })
                .catch(error => console.error(error));


    if (organisationId !== "organisation" && organisations === false) {
        const url2 = `/api/couch/organisation/${organisationId}`;
        fetch(url2, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
            if (!response.ok) {
                throw new Error("Неверная организация")
            }
            organisations = true;
            return response.json();
        })
            .catch(error => console.error(error));
    }else {
        $("#confirmationModal").modal('show');
        return;
    }

    window.location.href = '/couches';
}


//
// <!DOCTYPE html>
// <html lang="en">
//
//     <head>
//     <meta charset="UTF-8">
//     <meta name="viewport" content="width=device-width, initial-scale=1.0">
//     <title>Выбор организации и тренера</title>
// <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
// </head>
//
// <body>
// <div class="container mt-5">
//     <div class="row justify-content-center">
//         <div class="col-md-6">
//             <div class="card shadow-sm">
//                 <div class="card-header text-center bg-primary text-white">
//                     <h3 class="card-title">Выберите организацию и подходящего тренера</h3>
//                 </div>
//                 <div class="card-body">
//
//                     <div class="mb-3">
//                         <label for="organisationType" class="form-label">Выберите организацию:</label>
//                         <select id="organisationType" class="form-select">
//                             <option value="organisation" disabled selected hidden>Организация</option>
//                         </select>
//                     </div>
//                     <div class="mb-3">
//                         <label for="sportType" class="form-label">Выберите спорт:</label>
//                         <select id="sportType" class="form-select">
//                             <option value="sport" disabled selected hidden>Спорт</option>
//                         </select>
//                     </div>
//                     <div class="mb-3">
//                         <label for="couchType" class="form-label">Выберите тренера:</label>
//                         <select id="couchType" class="form-select">
//                             <option value="couch" disabled selected hidden>Тренер</option>
//                         </select>
//                     </div>
//                     <form id="profile">
//                         <h4 class="mb-3">Заполните данные для пользователя</h4>
//                         <div class="mb-3">
//                             <label for="firstName" class="form-label">Имя:</label>
//                             <input type="text" id="firstName" name="firstName" class="form-control">
//                         </div>
//                         <div class="mb-3">
//                             <label for="lastName" class="form-label">Фамилия:</label>
//                             <input type="text" id="lastName" name="lastName" class="form-control">
//                         </div>
//                         <div class="mb-3">
//                             <label for="height" class="form-label">Рост:</label>
//                             <input type="text" id="height" name="height" class="form-control">
//                         </div>
//                         <div class="mb-3">
//                             <label for="weight" class="form-label">Вес:</label>
//                             <input type="text" id="weight" name="weight" class="form-control">
//                         </div>
//                         <div class="d-grid gap-2">
//                             <button type="button" class="btn btn-primary" id="next" onclick="next()">Дальше</button>
//                             <button type="button" class="btn btn-secondary" id="skip" onclick="skip()">Пропустить</button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//         </div>
//     </div>
// </div>
// <script src="/js/enterUserForOrganisation.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
// </body>
//
// </html>

// const profileForm = document.getElementById('profile');
//
// const formData = new FormData(profileForm);
// const profileData = {
//     name: formData.get('firstName'),
//     surname: formData.get('lastName'),
//     height: formData.get('height'),
//     weight: formData.get('weight')
// };
//
// fetch('/api/user/', {
//     method: 'PUT',
//     headers: {
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(profileData)
// })
//     .then(response => response.json())
//     .then(data => {
//         alert('Данные профиля обновлены');
//         profileForm.reset();
//     });

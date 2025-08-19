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

// function skipping(){
//     const couchName = document.getElementById("name").value;
//     const couchExperience = document.getElementById("experience").value;
//     const couchPhoto = document.getElementById("photo").files[0];
//
//
//     const formData = new FormData();
//     formData.append('name', couchName);
//     formData.append('experience', couchExperience);
//     if (couchPhoto) {
//         formData.append('photo', couchPhoto)
//     }
//
//     const urlCouch = "/api/couch/skip/";
//
//     fetch(urlCouch, {
//         method: 'POST',
//         // headers: {'Content-type': 'application/json'},
//         body: formData
//     }).then(response => {
//         if (!response.ok) {
//             throw new Error("Нужно зарегестрировать тренера")
//         }
//         return response.json();
//     })
//         .then(data => {
//             console.log("Успех:", data);
//             window.location.href = '/couches';
//         }).catch(error => {
//         console.error("Ошибка:", error);
//     });
//
// }


function skip() {
    window.location.href = '/couches';
}

function closeConfirmationModal() {
    const hidden = document.getElementById("confirmationModal")
    $("#confirmationModal").modal('hide');
}

function next() {
    const getAllSportForOrganisation = document.getElementById("sportType");
    const getAllOrganisation = document.getElementById("organisationType");
    const sportId = getAllSportForOrganisation.value;
    const organisationId = getAllOrganisation.value;
    const sports = getAllSportForOrganisation.value;

    console.log(sportId);
    console.log(organisationId);

    const couchName = document.getElementById("name").value;
    const couchExperience = document.getElementById("experience").value;
    const couchPhoto = document.getElementById("photo").files[0];

    const formData = new FormData();
    formData.append('name', couchName);
    formData.append('experience', couchExperience);
    if (couchPhoto) {
        formData.append('photo', couchPhoto)
    }


    // let body;
    // if (couchPhoto){
    //     body = { name:couchName, experience:couchExperience, photo:couchPhoto}
    // } else {
    //     body = { name: couchName, experience: couchExperience}
    // }

    const urlCouch = "/api/couch/update/";
    console.log(formData);
    fetch(urlCouch, {
        method: 'PUT',
        // headers: {'Content-type': 'application/json'},
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error("Нужно зарегестрировать тренера")
        }
        return response;
    })
        .then(data => {
            console.log("Успех:", data);
            // window.location.href = '/couches';
        }).catch(error => {
        console.error("Ошибка:", error);
    });

    if (organisationId !== "organisation" && organisations === false) {
        const url = `/api/sport-section/sport/organisation/${sportId}/${organisationId}`;
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Неверная организация")
                }
                return response.json();
            })

            .then(data => {
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
                        window.location.href = '/couches';
                        return response;
                    })

            })
            .catch(error => console.error(error));
    } else {
        $("#confirmationModal").modal('show');
    }

}

const checkboxTerms = document.getElementById('agree-terms');
const checkboxPrivacy = document.getElementById('agree-privacy');
const enterButton = document.getElementById('skip');
const registerButton = document.getElementById('next');

function updateButtonState() {
    const isBothChecked = checkboxTerms.checked && checkboxPrivacy.checked;
    enterButton.disabled = !isBothChecked;
    registerButton.disabled = !isBothChecked;
}

// Обновлять состояние кнопок при каждом изменении галочек
checkboxTerms.addEventListener('change', updateButtonState);
checkboxPrivacy.addEventListener('change', updateButtonState);

// Вызовем один раз при загрузке
updateButtonState();

document.addEventListener('DOMContentLoaded', () => {
    const agreedTerms = localStorage.getItem('agreedTerms') === 'true';
    const agreedPrivacy = localStorage.getItem('agreedPrivacy') === 'true';

    if (agreedTerms && agreedPrivacy) {
        // Скрываем блок с чекбоксами
        document.querySelectorAll('.form-check').forEach(el => el.style.display = 'none');
        enterButton.disabled = false;
        registerButton.disabled = false;
    } else {
        // Иначе — поведение как раньше
        checkboxTerms.checked = agreedTerms;
        checkboxPrivacy.checked = agreedPrivacy;
        updateButtonState();
    }
});


function openLegalDoc(url) {
    const pdfFrame = document.getElementById("pdfFrame");
    pdfFrame.src = url;
    const modal = new bootstrap.Modal(document.getElementById('pdfModal'));
    modal.show();
}


// document.getElementById('modal-continue').addEventListener('click', function () {
//     const termsChecked = document.getElementById('modal-agree-terms').checked;
//     const privacyChecked = document.getElementById('modal-agree-privacy').checked;
//
//     if (!termsChecked || !privacyChecked) {
//         document.getElementById('modal-error').style.display = 'block';
//         return;
//     }
//
//     localStorage.setItem('agreedTerms', true);
//     localStorage.setItem('agreedPrivacy', true);
//
//     bootstrap.Modal.getInstance(document.getElementById('legalConfirmModal')).hide();
//
//     // performRegistration(); // регистрируем после подтверждения
// });


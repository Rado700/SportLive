document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('my-container')

    const profileTraining = document.getElementById('profile-training');
    const trainingScreen = document.getElementById('trainingScreen');

    const profileInventory = document.getElementById('profile-inventory');

    // const infoScreen = document.getElementById('info-screen');
    // const statistics = document.getElementById('statistics');
    const refreshButton = document.getElementById('refresh-data');


    // const selectOrganization = document.getElementById('selectOrganization');
    // const addSport = document.getElementById('addSport');
    // const addCouch = document.getElementById('addCouch');

    const trainerInfo = document.getElementById('trainer-info');
    const profileInfo = document.getElementById('profile-info');
    const equipmentInfo = document.getElementById('equipment-info');

    // const modal = document.getElementById("cashBack-modal");
    // const cashBack = document.getElementById("cashBack");
    const confirmCashBack = document.getElementById("confirm-cashBack");

    const showScreen = (screen) => {
        // modal.classList.add('hidden');
        // trainingScreen.classList.add('hidden');
        // inventoryScreen.classList.add('hidden');
        // container.classList.add('hidden');
        // infoScreen.classList.add('hidden');
        screen.classList.remove('hidden');
    };


    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if (urlParams.get("page") === "withdrawBalanceForCouchScreen") {
        const modal = new bootstrap.Modal(document.getElementById("cashBack-modal"));
        modal.show();
    }
    if (urlParams.get("page") === "addScheduleScreen") {
        const modal = new bootstrap.Modal(document.getElementById("trainingModal"));
        modal.show();
    }
    if (urlParams.get("page") === "scheduleForCouchScreen") {
        const modal = new bootstrap.Modal(document.getElementById("scheduleForCouch"));
        modal.show();
    }
    if (urlParams.get("page") === "addInventoryForCouchScreen") {
        const modal = new bootstrap.Modal(document.getElementById("inventoryModal"));
        modal.show();
    }


// Действие при нажатии на кнопку "Вывод средств"
    document.getElementById('cashBack').addEventListener("click", function () {
        const modal = new bootstrap.Modal(document.getElementById("cashBack-modal"));
        modal.show();
        const infoScreenModal = new bootstrap.Modal(document.getElementById("inventoryScreenModal"));
        infoScreenModal.hide();


        confirmCashBack.onclick = function () {
            // let amounts = document.getElementById("amounts").value;
            alert("Вывод временно не работает")
            // fetch("/yoomoney/getInvoicePay/" + amounts, {
            //     method: "GET",
            //     headers: {'Content-type': 'application/json'}
            // })
            //     .then(response => {
            //         console.log(response);
            //         if (!response.ok) {
            //             throw new Error("Не получается оплатить")
            //         }
            //         return response.text()
            //             .then(data => {
            //                 window.location.href = data;
            //             })
            //     })

        }
    });

    function popupShow(text) {
        const popup = document.getElementById("popup");
        popup.innerHTML = text;
        // Показываем всплывающее окно
        popup.classList.add("show");

        // Скрываем его через 3 секунды
        setTimeout(() => {
            popup.classList.remove("show");
        }, 3000);
    }


    // Добавление расписания (Общие, индивидуальные)Создание кнопок добавления и удаление времени

    //Выбор тип тренировки
    document.getElementById("training_type").addEventListener("change", function () {
        const type = document.getElementById("training_type").value;
        const general = document.getElementById("addDaysForGeneral");
        const individual = document.getElementById("addDaysForIndividual");

        if (type === "individual") {
            general.style.display = "none";
            individual.style.display = "block";
        } else {
            individual.style.display = "none";
            general.style.display = "block";
        }
    })

    const general = document.getElementById("addDaysForGeneral");
    const individual = document.getElementById("addDaysForIndividual");
    general.style.display = "none";
    individual.style.display = "block";


    // Обработчик для кнопок выбора дня недели
    document.querySelectorAll('.day-button').forEach(button => {
        button.addEventListener('click', function () {
            const day = this.getAttribute('data-day');
            console.log(this.classList.contains('active-button'));
            if (this.classList.contains('active-button')) {
                this.classList.remove('active-button');
                this.classList.add('inactive-button');
            } else {
                this.classList.remove('inactive-button');
                this.classList.add('active-button');
            }
        });
    });


    // Добавление времени
    document.getElementById('nextTimes').addEventListener('click', function () {
        const addTime = document.getElementById("allTimes");
        const selectedDays = [];
        const allWeekDays = document.querySelectorAll('.day-button');
        allWeekDays.forEach(day => {
            if (day.classList.contains("active-button")) {
                selectedDays.push(parseInt(day.getAttribute("data-day")))
            }
        })
        // Проверяем, выбраны ли дни недели
        if (selectedDays.length === 0) {
            alert('Выберите хотя бы один день недели.');
            return;
        }

        // Создаем контейнер для input и крестика
        const timeContainer = document.createElement("div");
        timeContainer.setAttribute("class", "time-container");
        timeContainer.style.display = "flex";
        timeContainer.style.alignItems = "center";
        // timeContainer.style.marginBottom = "10px";

        // Создаем input для времени
        const dateTimeInput = document.createElement("input");
        dateTimeInput.setAttribute("type", "time");
        dateTimeInput.setAttribute("name", "time");
        dateTimeInput.setAttribute("class", "form-control");
        dateTimeInput.setAttribute("required", true);
        dateTimeInput.style.marginRight = "10px"; // Отступ справа для крестика


        // Создаем кнопку крестика для удаления
        const deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button"); // Кнопка без отправки формы
        deleteButton.innerHTML = "&times;"; // Символ крестика
        deleteButton.style.backgroundColor = "transparent"; // Прозрачный фон
        deleteButton.style.border = "none"; // Убираем границу
        deleteButton.style.fontSize = "20px"; // Размер текста крестика
        deleteButton.style.cursor = "pointer"; // Изменение курсора при наведении
        deleteButton.style.margin = "0px";

        // Добавляем обработчик для удаления input и крестика
        deleteButton.addEventListener("click", function () {
            addTime.removeChild(timeContainer);
        });

        // Добавляем input и крестик в контейнер
        timeContainer.appendChild(dateTimeInput);
        timeContainer.appendChild(deleteButton);

        // Добавляем контейнер в DOM
        addTime.appendChild(timeContainer);
    });

    function setSportSection(data){
        const sportSectionSelect = document.getElementById("sportSections");
        const option = document.createElement("div");
        data.forEach(item => {
            option.value = item.id;
            option.textContent = item.name;
            sportSectionSelect.appendChild(option);
        })
    }

    //Добавить тренировку
    document.getElementById('addTraining').addEventListener('click', function () {
        const sportSectionSelect = document.getElementById("sportSections");
        sportSectionSelect.innerHTML = '';

        fetch("/api/couch/sport-section/")
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
                setSportSection(data);

                const sportSectionSelectForTariffs = document.getElementById("sportSectionsForTariffs");
                const option2 = document.createElement("option");
                option2.value = sportSection.id;
                option2.textContent = sportSection.name;
                sportSectionSelectForTariffs.appendChild(option2);

            // showScreen(trainingScreen);
        })

    });

    // Настроить время (начало с сегодняшнего дня)
    function todayDay() {
        let currentDateTime = new Date().toISOString();
        console.log(currentDateTime)
        return currentDateTime.split("Z")[0].slice(0, -7);
    }

    document.getElementById("date").setAttribute("min", todayDay())

    // добавляет блок времени +1
    document.getElementById('nextTime').addEventListener('click', function () {

        const addTime = document.getElementById("allTime");

        // Создаем контейнер для input и крестика
        const timeContainer = document.createElement("div");
        timeContainer.setAttribute("class", "time-container");
        timeContainer.style.display = "flex";
        timeContainer.style.alignItems = "center";

        // Создаем input для даты и времени
        const dateTimeInput = document.createElement("input");
        dateTimeInput.setAttribute("type", "datetime-local");
        dateTimeInput.setAttribute("name", "date");
        dateTimeInput.setAttribute("class", "form-control");
        dateTimeInput.setAttribute("required", true);
        dateTimeInput.style.marginRight = "10px"; // Отступ справа для крестика

        // Создаем кнопку крестика для удаления
        const deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button"); // Кнопка без отправки формы
        deleteButton.innerHTML = "&times;"; // Символ крестика
        deleteButton.style.backgroundColor = "transparent"; // Прозрачный фон
        deleteButton.style.border = "none"; // Убираем границу
        deleteButton.style.fontSize = "20px"; // Размер текста крестика
        deleteButton.style.cursor = "pointer"; // Изменение курсора при наведении
        deleteButton.style.margin = "0px";

        // Добавляем обработчик для удаления input и крестика
        deleteButton.addEventListener("click", function () {
            addTime.removeChild(timeContainer);
        });

        // Добавляем input и крестик в контейнер
        timeContainer.appendChild(dateTimeInput);
        timeContainer.appendChild(deleteButton);

        // Добавляем контейнер в DOM
        addTime.appendChild(timeContainer);
    });

    function newData() {
        const placeId = document.getElementById("place");
        const descriptionId = document.getElementById("description");
        const sumId = document.getElementById("sum");
        const startDateId = document.getElementById("startDate");
        const endDateId = document.getElementById("endDate");
        const dateId = document.getElementById("date");
        const nextTimesId = document.getElementById("nextTimes");

        placeId.value = "";
        descriptionId.value = "";
        sumId.value = "";
        startDateId.value = "";
        endDateId.value = "";
        dateId.value = "";
        nextTimesId.value = "";
    }

    // Добавление общих и индивидуальных тренировок
    profileTraining.addEventListener('submit', (e) => {
        e.preventDefault();

        if (!profileTraining.checkValidity()) {
            profileTraining.reportValidity();
            return;
        }

        const type = document.getElementById("training_type").value;

        // Добавить в общее расписание
        if (type === "general") {
            console.log(type);
            const currentSelectedDays = [];
            const allWeekDays = document.querySelectorAll('.day-button');
            allWeekDays.forEach(day => {
                if (day.classList.contains("active-button")) {
                    currentSelectedDays.push(parseInt(day.getAttribute("data-day")))
                }
            })
            const dayOfNow = new Date.now(document.getElementById("startDate").value);
            const dayOfEnd = new Date(document.getElementById("endDate").value);
            console.log(currentSelectedDays)

            for (let i = dayOfNow; i <= dayOfEnd; i.setDate(i.getDate() + 1)) {
                console.log(i.getDay())

                if (currentSelectedDays.includes(i.getDay())) {
                    const allTime = document.getElementsByName("time");
                    allTime.forEach(time => {
                        const [hours, minutes] = time.value.split(":"); // ["11", "22"]
                        const formData = new FormData(profileTraining);

                        const today = new Date(i); // или любая дата, которую вы хотите
                        if (new Date() <= today){

                        const yyyy = today.getFullYear();
                        const MM = String(today.getMonth() + 1).padStart(2, "0");
                        const dd = String(today.getDate()).padStart(2, "0");
                        const HH = String(hours).padStart(2, "0");
                        const mm = String(minutes).padStart(2, "0");

                        // Собираем локальное время в ISO 8601 без таймзоны
                        const dateString = `${yyyy}-${MM}-${dd}T${HH}:${mm}:00`;

                        const profileData = {
                            place: formData.get("place") || "Не указано",
                            description: formData.get("description") || "Не указано",
                            sum: formData.get("sum") || 0,
                            typeWorkout: type,
                            date: dateString,
                        };

                        console.log(profileData)
                        const sportSectionSelect = document.getElementById("sportSections");
                        const sportSectionId = parseInt(sportSectionSelect.value);

                        fetch('/api/schedule/' + sportSectionId, {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(profileData)
                        })
                            .then(response => response.json())
                            .then(data => {
                                popupShow("Общая тренировка добавлена!")
                                newData();
                            })
                            .catch(error => console.error('Ошибка:', error));

                        }
                    })
                }
            }
        }
        //Добавление индивидуальных тренировок
        if (type === "individual") {

            const allDate = document.getElementsByName("date");
            allDate.forEach(date => {

                const formData = new FormData(profileTraining);
                const profileData = {
                    place: formData.get('place') || "Не указано",
                    description: formData.get('description') || "Не указано",
                    typeWorkout: type,
                    sum: formData.get('sum') || 0,
                    date: date.value,

                };

                const sportSectionSelect = document.getElementById("sportSections");
                const sportSectionId = parseInt(sportSectionSelect.value);


                fetch('/api/schedule/' + sportSectionId, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(profileData)
                }).then(response => response.json())
                    .then(data => {
                        popupShow("Индивидуальная тренировка добавлена!")
                        newData();

                    })
                    .catch(error => console.error('Ошибка:', error));

            })

        }
        const modalElement = document.getElementById('trainingModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();

    });

    //Добавить тариф
    function generateUUID() {
        return crypto.randomUUID();
    }

    // Генерация при открытии модального окна
    const seasonModal = document.getElementById('seasonTicketModal');
    seasonModal.addEventListener('show.bs.modal', () => {
        document.getElementById('seasonUuid').value = generateUUID();
    });

    // Обработка отправки формы
    document.getElementById('seasonTicketForm').addEventListener('submit', function (e) {
        e.preventDefault();

        document.querySelectorAll('[name="tariffTime"]').forEach(tariffTime =>{

            const sportSection = document.getElementById("sportSectionsForTariffs").value;

            const form = new FormData(this);
            const ticketData = Object.fromEntries(form.entries());
            ticketData.sectionId = parseInt(sportSection);
            ticketData.dayOfWeek = tariffTime.querySelector('[name="day"]').value;
            ticketData.time = tariffTime.querySelector('[name="time"]').value;
            console.log(ticketData)
            // Отправка на сервер (замени URL)
            fetch('/api/seasonTickets/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ticketData)
            }).then(response => {
                if (response.ok) {
                    this.reset();
                    selectedDays.clear();
                    popupShow("Абонемент успешно добавлен!");
                    document.querySelectorAll('.day-btn').forEach(btn => {
                        btn.classList.remove('btn-primary');
                        btn.classList.add('btn-outline-secondary');
                    });
                    bootstrap.Modal.getInstance(seasonModal).hide();
                } else {
                    alert('Ошибка при сохранении.');
                }
            });
        })


    });

    document.getElementById("nextTimeTariff").addEventListener('click', function () {
        const container = document.getElementById('allTimesTariff');

        const wrapper = document.createElement('div');
        wrapper.className = 'd-flex mb-2';
        wrapper.name = 'tariffTime';

        const select = document.createElement('select');
        select.className = 'form-select';
        select.name = 'day';
        select.innerHTML = `
            <option value="Понедельник">Понедельник</option>
            <option value="Вторник">Вторник</option>
            <option value="Среда">Среда</option>
            <option value="Четверг">Четверг</option>
            <option value="Пятница">Пятница</option>
            <option value="Суббота">Суббота</option>
            <option value="Воскресенье">Воскресенье</option>
        `;

        const input = document.createElement('input');
        input.type = 'time';
        input.className = 'form-control';
        input.name = 'time';
        input.required = true;


        // Создаем кнопку крестика для удаления
        const deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button"); // Кнопка без отправки формы
        deleteButton.innerHTML = "&times;"; // Символ крестика
        deleteButton.style.backgroundColor = "transparent"; // Прозрачный фон
        deleteButton.style.border = "none"; // Убираем границу
        deleteButton.style.fontSize = "20px"; // Размер текста крестика
        deleteButton.style.cursor = "pointer"; // Изменение курсора при наведении
        deleteButton.style.margin = "0px";
        deleteButton.style.padding = "0px 0px 0px 7px";
        deleteButton.style.width = '25px';

        // Добавляем обработчик для удаления input и крестика
        deleteButton.addEventListener("click", function () {
            container.removeChild(wrapper);
        });

        wrapper.appendChild(select);
        wrapper.appendChild(input);
        wrapper.appendChild(deleteButton);
        container.appendChild(wrapper);

    })



    // Вывод расписание для тренера(Индивидуальные,Общие)

    document.getElementById('scheduleForCouch').addEventListener('shown.bs.modal', function () {
        const sportSectionSelect = document.getElementById("sports-section");
        sportSectionSelect.innerHTML = "";

        fetch("/api/couch/sport-section/")
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
            data.forEach(sportSection => {
                const sportSectionSelect = document.getElementById("sports-section");
                const option = document.createElement("option");
                option.value = sportSection.id;
                option.textContent = sportSection.name;
                sportSectionSelect.appendChild(option);
            })
            // showScreen(trainingScreen);
        })

        getSchedule();
        const currentMonth = new Date().getMonth();
        document.getElementById('month').value = currentMonth;
        generateCalendarToCouch(currentMonth);
        tariffsForCouch();
    });

    const couchTypeButtons = document.querySelectorAll('.schedule-type-btn');
    couchTypeButtons.forEach(button => {
        button.addEventListener('click', function () {
            couchTypeButtons.forEach(btn => {
                // Убираем выделение со всех кнопок
                couchTypeButtons.forEach(btn => btn.classList.remove('btn-primary', 'text-white'));
                couchTypeButtons.forEach(btn => btn.classList.add('btn-outline-primary'));
            });
            // Добавляем выделение для выбранной кнопки
            this.classList.remove('btn-outline-primary');
            this.classList.add('btn-primary', 'text-white');

            // Устанавливаем выбранный тип пользователя
            selectedUserType = this.getAttribute('data-type');
            console.log("Выбранный тип пользователя:", selectedUserType);
            getSchedule();
        });
    });

    let buttonEvents = {};


    //Инвентарь

    profileInventory.addEventListener('submit', (ev) => {
        ev.preventDefault();
        const formData = new FormData(profileInventory);
        const profileDate = {
            name: formData.get('name'),
            type: formData.get('type'),
            size: formData.get('size'),
            price: formData.get('price'),
            amount: formData.get('amount'),
        }

        fetch('/api/inventory/couch/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileDate)
        }).then(response => response.json())
            .then(data => popupShow("Добавлен инвентарь: " + profileDate.name))
            .catch(error => console.error('Ошибка:', error));

        // Закрыть модалку программно
        const inventoryModal = bootstrap.Modal.getInstance(document.getElementById('inventoryModal'));
        if (inventoryModal) {
            inventoryModal.hide();
        }

        profileInventory.reset();

    });

    // Инвентарь

    document.getElementById("inventory").addEventListener("click", function () {
        const inventoryModalScreen = new bootstrap.Modal(document.getElementById("inventoryModalScreen"));
        inventoryModalScreen.show();

        // Элемент, куда будет добавляться информация
        const equipmentInfo = document.getElementById('equipment-info');
        const equipmentInfoSpan = equipmentInfo.querySelector('span');

        // Функция обработки данных инвентаря
        const processInventoryData = (data) => {
            console.log("Обработка инвентаря...");
            let infoBox2 = document.createElement('div');
            infoBox2.classList.add('info-box2');
            infoBox2.style.display = 'flex';
            infoBox2.style.flexWrap = 'wrap';

            data.forEach(profile => {
                console.log("Обработка профиля:", profile);

                const profileDiv = document.createElement("div");
                profileDiv.classList.add("profile-item");
                profileDiv.style.border = "1px solid #ccc"; // Добавляем немного стилей
                profileDiv.style.margin = "10px";
                profileDiv.style.padding = "10px";

                // Добавляем данные профиля
                const name = document.createElement("p");
                name.textContent = `Наименования: ${profile.name || "Не указано"}`;
                profileDiv.appendChild(name);

                const price = document.createElement("p");
                price.textContent = `Цена: ${profile.price || 0}`;
                profileDiv.appendChild(price);

                const type = document.createElement("p");
                type.textContent = `Тип: ${profile.type || "Не указано"}`;
                profileDiv.appendChild(type);

                const size = document.createElement("p");
                size.textContent = `Размер: ${profile.size || "Не указано"}`;
                profileDiv.appendChild(size);

                const amount = document.createElement("p");
                amount.textContent = `Количество: ${profile.amount || 0}`;
                profileDiv.appendChild(amount);


                // Кнопка "Удалить"
                const deleteButton = document.createElement("button");
                deleteButton.classList.add("delete-button");
                deleteButton.textContent = "Удалить";
                deleteButton.style.backgroundColor = "aqua";
                deleteButton.style.fontSize = "15px";
                deleteButton.style.cursor = "pointer";
                deleteButton.style.borderRadius = "2px";

                profileDiv.appendChild(deleteButton);

                // Добавляем обработчик для удаления
                deleteButton.addEventListener('click', function () {
                    deleteInventory(profile.id, profileDiv);
                });

                // Добавляем профиль в infoBox
                infoBox2.appendChild(profileDiv);
            });

            return infoBox2;
        };

        // Функция для загрузки данных с сервера
        const fetchData = () => {
            // Показываем, что данные загружаются
            equipmentInfoSpan.textContent = "Загрузка...";

            fetch('/api/inventory/couchInventory/')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка HTTP: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Инвентарь загружен:", data);

                    // Отображаем общее количество
                    equipmentInfoSpan.textContent = data.length;

                    // Удаляем старое содержимое и добавляем новое
                    const inventoryResult = processInventoryData(data);
                    if (equipmentInfo.nextElementSibling) {
                        equipmentInfo.nextElementSibling.remove();
                    }
                    equipmentInfo.after(inventoryResult);
                })
                .catch(error => {
                    console.error("Ошибка загрузки данных:", error);
                    equipmentInfoSpan.textContent = `Ошибка: ${error.message}`;
                });
        };

        fetchData();
    });

// Функция для удаления элемента инвентаря
    function deleteInventory(id, profileDiv) {
        fetch(`/api/inventory/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    console.log(`Удален инвентарь с ID: ${id}`);
                    profileDiv.remove(); // Удаляем элемент из DOM
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || "Ошибка при удалении");
                    });
                }
            })
            .catch(error => {
                console.error("Ошибка удаления:", error);
                alert(`Не удалось удалить элемент: ${error.message}`);
            });
    }

    //Статистика

    document.getElementById("statistics").addEventListener("click", function () {
        const infoScreenModal = new bootstrap.Modal(document.getElementById("inventoryScreenModal"));
        infoScreenModal.show();

        // Функция обработки данных профиля
        const processProfileData = (data) => {
            let infoBox3 = document.createElement('div');
            infoBox3.classList.add('info-box3');
            infoBox3.display = 'flex';


            const name = data.name || "Имя отсутствует";
            const balance = data.balance || 0;
            const experience = data.experience || "Стаж не указано"
            const login = data.login?.login || "Login не найден";

            infoBox3.innerHTML = `
            <h4>Профиль:</h4>
            <p><strong>Имя:</strong> ${name}</p>
            <p><strong>Баланс:</strong> ${balance}</p>
            <p><strong>Стаж:</strong> ${experience}</p>
            <p><strong>Логин:</strong> ${login}</p>`;

            return infoBox3;

        };

        // Функция обработки данных расписания
        const processBookingData = (count) => {
            console.log("booking")
            let infoBox3 = document.createElement('div');
            infoBox3.classList.add('info-box3');
            infoBox3.display = 'flex';

            const countBooking = count > 0 ? count : "Нету забронированных тренировок";

            infoBox3.innerHTML = `
            <h4>Зал:</h4>
            <p><strong>Осталось тренировок:</strong> ${countBooking}</p>`;
            return infoBox3;

        };

        const processUserAll = (count) => {

            let infoBox3 = document.createElement('div');
            infoBox3.classList.add('info-box3');
            infoBox3.display = 'flex';

            const countPeople = count > 0 ? count : "Нету зарегестрированных учеников"

            infoBox3.innerHTML += `
            <p><strong>Количество учеников:</strong> ${countPeople}</p>
            <button id="showAllUsers" class="btn btn-primary">Показать всех учеников</button>
            <div id="userList" style="display: none;"></div>
          `;
            return infoBox3;

        };

        // Обратобка
        const fetchData = () => {

            // Обработка расписания
            fetch('/api/booking/couchBooking/')
                .then(response => response.json())
                .then(data => {
                    let count = 0;
                    trainerInfo.innerHTML = '';
                    data.forEach(countAll => {
                        if (countAll !== null) {
                            count++;

                        }
                    });
                    const bookingResult = processBookingData(count);
                    trainerInfo.appendChild(bookingResult);

                })
                .catch(error => {
                    trainerInfo.innerHTML = 'Ошибка загрузки данных расписания: ' + error;
                });

            fetch('/api/couch/allUserForCouch/')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                }).then(data => {
                let count = 0;
                data.forEach(countAll => {
                    if (countAll !== null) {
                        count++;
                    }
                })
                const userCountInfo = processUserAll(count);
                trainerInfo.appendChild(userCountInfo);
                document.getElementById("showAllUsers").addEventListener("click", function () {
                    fetchUserList();
                });
            })
            // Обработка профиля
            fetch('/api/couch/getCouch/')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("PROFILE")
                    const profileResult = processProfileData(data);
                    // Очищаем контейнер перед вставкой
                    profileInfo.innerHTML = '';
                    profileInfo.appendChild(profileResult);
                })
                .catch(error => {
                    profileInfo.innerHTML = `<p>Ошибка загрузки данных профиля: ${error.message}</p>`;
                });
        };


        const fetchUserList = () => {
            fetch('/api/couch/allUserForCouch/')
                .then(response => response.json())
                .then(data => {
                    let userListDiv = document.getElementById("userList");
                    userListDiv.style.display = 'block';
                    userListDiv.innerHTML = '<h4>Список учеников:</h4>';
                    userListDiv.style.color = 'black';
                    data.forEach(user => {
                        userListDiv.innerHTML += `<p>${user.name} ${user.surname}</p>`;
                    });
                    userListDiv.innerHTML += '<button class="btn btn-primary mt-3" id="closeUserList">▲ Закрыть список</button>';
                    document.getElementById("closeUserList").addEventListener("click", function () {
                        userListDiv.style.display = "none";
                    })
                })
                .catch(error => {
                    document.getElementById("userList").innerHTML = `<p>Ошибка загрузки списка учеников: ${error.message}</p>`;
                });
        };

        // Обновление данных при нажатии кнопки
        refreshButton.addEventListener('click', fetchData);

        // Первоначальная загрузка
        fetchData();


    });

    // document.getElementById('back-to-main-inventory').addEventListener('click', () => {
    //     window.location.display.style.label = "close";
    // });
    // document.getElementById('back-to-main-timer').addEventListener('click', () => {
    //     window.location.display.style.label = "none";
    //
    // });


    // // Select Organization
    // document.getElementById('selectOrganization').addEventListener('click', function () {
    //     const orgName = prompt("Введите название организации:");
    //     if (orgName) {
    //         fetch(`${apiUrl}/organisation-controller`, {
    //             method: 'POST',
    //             headers: {'Content-Type': 'application/json'},
    //             body: JSON.stringify({name: orgName})
    //         }).then(response => response.json())
    //             .then(data => alert('Организация выбрана: ' + JSON.stringify(data)))
    //             .catch(error => console.error('Ошибка:', error));
    //     }
    // });

    // // Add Sport
    // document.getElementById('addSport').addEventListener('click', function () {
    //     const sportName = prompt("Введите название вида спорта:");
    //     if (sportName) {
    //         fetch(`${apiUrl}/sport-controller`, {
    //             method: 'POST',
    //             headers: {'Content-Type': 'application/json'},
    //             body: JSON.stringify({name: sportName})
    //         }).then(response => response.json())
    //             .then(data => {
    //                 alert('Вид спорта добавлен: ' + JSON.stringify(data));
    //                 fetch(`${apiUrl}/booking-controller`)
    //                     .then(res => res.json())
    //                     .then(bookings => alert(`Общее количество брони: ${bookings.length}`));
    //             })
    //             .catch(error => console.error('Ошибка:', error));
    //     }
    // });


    // document.getElementById('addExercises').addEventListener('click', function () {
    //     let exerciseInputs = '';
    //     for (let i = 1; i <= 5; i++) {
    //         exerciseInputs += `
    //         <label for="exercise${i}" class="form-label">Упражнение ${i}</label>
    //         <input type="text" class="form-control mb-3" id="exercise${i}" name="exercise${i}" placeholder="Введите название упражнения">
    //     `;
    //     }
    //     document.getElementById('exerciseInputs').innerHTML = exerciseInputs;
    //
    // });

// Добавление заметок

    document.getElementById('save-notes').addEventListener('click', () => {
        const notes = document.getElementById('notes').value;
        // notes = notes.replace(/\n/g, ' ');
        const formattedText = notes.replace(/\\n/g, '\n').replace(/\n/g, '\n');
        // Send notes to backend
        fetch('/api/couch/addNotesForCouch', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({notes:formattedText})
        })
            .then(response => response.json())
            .then(data => {
                alert('Заметки сохранены');
                document.getElementById("notes").value = '';
            });
    });

    document.getElementById('show-notes').addEventListener('click', () => {
        fetch('/api/couch/getCouchNotes')
            .then(response => response.json())
            .then(data => {
                const notesContainer = document.getElementById('all-notes');
                notesContainer.innerHTML = ''; // очищаем перед добавлением

                if (data.length === 0) {
                    notesContainer.innerHTML = '<p>Заметок пока нет.</p>';
                    return;
                }

                const sortedNotes = data.sort((a, b) => new Date(b.dateTime) - new Date(a.dateTime));
                const lastNotes = sortedNotes.slice(0, 3);
                const remainingNotes = sortedNotes.slice(3);

                // Отображаем последние 3
                lastNotes.forEach(note => {
                    const noteElement = document.createElement('div');
                    noteElement.className = 'note-card';
                    noteElement.innerHTML = `
                    <small>${new Date(note.localDateTime).toLocaleString()}</small>
                    <p style="white-space: pre-line;">${note.text || note.notes}</p>
                `;
                    notesContainer.appendChild(noteElement);
                });

                // Если есть еще заметки, добавляем кнопку "Показать все"
                if (remainingNotes.length > 0) {
                    const showAllBtn = document.createElement('button');
                    showAllBtn.textContent = 'Показать все';
                    showAllBtn.id = 'show-all-btn';
                    showAllBtn.addEventListener('click', () => {
                        remainingNotes.forEach(note => {
                            const noteElement = document.createElement('div');
                            noteElement.className = 'note-card';
                            noteElement.innerHTML = `
                            <small>${new Date(note.dateTime).toLocaleString()}</small>
                            <p style="white-space: pre-line">${note.text || note.notes}</p>
                        `;
                            notesContainer.appendChild(noteElement);
                        });
                        showAllBtn.remove(); // убираем кнопку после показа всех
                    });
                    notesContainer.appendChild(showAllBtn);
                }

                document.getElementById('show-notes').style.display = 'none';
                document.getElementById('hide-notes').style.display = 'block';
            })
            .catch(err => {
                console.error('Ошибка при загрузке заметок:', err);
                alert('Ошибка при загрузке заметок');
            });
    });

    document.getElementById('hide-notes').addEventListener('click', () => {
        const notesContainer = document.getElementById('all-notes');
        notesContainer.innerHTML = '';
        document.getElementById('show-notes').style.display = 'block';
        document.getElementById('hide-notes').style.display = 'none';
    });




// Таймер


    let stopwatchInterval;
    let stopwatchTime = 0;
    const stopwatchDisplay = document.getElementById('stopwatchDisplay');

    document.getElementById('startStopwatch').addEventListener('click', function () {
        if (stopwatchInterval) return;
        stopwatchInterval = setInterval(() => {
            stopwatchTime++;
            const hours = Math.floor(stopwatchTime / 3600).toString().padStart(2, '0');
            const minutes = Math.floor((stopwatchTime % 3600) / 60).toString().padStart(2, '0');
            const seconds = (stopwatchTime % 60).toString().padStart(2, '0');
            stopwatchDisplay.textContent = `${hours}:${minutes}:${seconds}`;
        }, 1000);
    });

    document.getElementById('stopStopwatch').addEventListener('click', function () {
        clearInterval(stopwatchInterval);
        stopwatchInterval = null;
    });

    document.getElementById('resetStopwatch').addEventListener('click', function () {
        clearInterval(stopwatchInterval);
        stopwatchInterval = null;
        stopwatchTime = 0;
        stopwatchDisplay.textContent = '00:00:00';
    });

    let timerInterval;
    const timerDisplay = document.getElementById('timerDisplay');

    document.getElementById('startTimer').addEventListener('click', function () {
        const timerMinutes = parseInt(document.getElementById('timerMinutes').value);
        if (isNaN(timerMinutes) || timerMinutes <= 0) {
            alert('Введите действительное количество минут.');
            return;
        }
        let timerTime = timerMinutes * 60;
        timerInterval = setInterval(() => {
            if (timerTime <= 0) {
                clearInterval(timerInterval);
                timerDisplay.textContent = '00:00:00';
                alert('Таймер завершен!');
                return;
            }
            timerTime--;
            const minutes = Math.floor(timerTime / 60).toString().padStart(2, '0');
            const seconds = (timerTime % 60).toString().padStart(2, '0');
            timerDisplay.textContent = `00:${minutes}:${seconds}`;
        }, 1000);
    });

    document.getElementById('stopTimer').addEventListener('click', function () {
        clearInterval(timerInterval);
    });

    document.getElementById('resetTimer').addEventListener('click', function () {
        clearInterval(timerInterval);
        timerDisplay.textContent = '00:00:00';
        document.getElementById('timerMinutes').value = '';
    });


    const currentMonth = new Date().getMonth();
    document.getElementById('month').value = currentMonth;
    generateCalendarToCouch(currentMonth);

    openTgBot();

});

function exit() {

    fetch("/api/login/couch/exit")
        .then(response => {
            if (response.ok) {
                window.location.href = "/";
            }
        }).catch(error => console.error('Error:', error))
}

function openTgBot() {
    document.getElementById("notification").addEventListener("click", function () {
        fetch("/api/login/hashId/")
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.text();
            }).then(data => {
            window.location.href = "https://t.me/sportliveapp_bot?start=auth_" + data;
        })
    })
}


document.getElementById("month").addEventListener('change', function () {
    generateCalendarToCouch(this.value);
})

function generateCalendarToCouch(month) {

    const calendar = document.getElementById('calendar');
    calendar.innerHTML = '';
    let weekDays = ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"];
    for (let i = 0; i < 7; i++) {
        const emptySlot = document.createElement('div');
        emptySlot.style.textAlign = 'center';
        emptySlot.textContent = weekDays[i];
        calendar.appendChild(emptySlot);
    }

    const date = new Date();
    date.setMonth(month);
    date.setDate(1);
    let firstDay = date.getDay() - 1;
    if (firstDay < 0) {
        firstDay = 6;
    }

    const year = date.getFullYear();
    const currentMonth = date.getMonth();
    const firstDayOfNextMonth = new Date(year, currentMonth + 1, 1);
    const daysInMonth = new Date(firstDayOfNextMonth - 1).getDate();

    // let occupiedDays = [];
    // let days;
    //const  = new Date(date.getFullYear(), month + 1, 0).getDate();
    console.log(firstDay)
    console.log(daysInMonth)
    // Fill initial empty slots
    for (let i = 0; i < firstDay; i++) {
        const emptySlot = document.createElement('div');
        calendar.appendChild(emptySlot);
    }


    // Create buttons for each day of the month
    for (let day = 1; day <= daysInMonth; day++) {
        const dayButton = document.createElement('button');
        dayButton.id = 'button_' + day;
        dayButton.name = 'dayButton';
        dayButton.textContent = day;
        days = dayButton.id;


        // if (occupiedDays.includes(day)) {
        //     dayButton.style.backgroundColor = "red"; // Подкрашиваем занятый день в красный
        // }


        dayButton.addEventListener('click', function (event) {
            // Снимаем выделение с предыдущего выбранного дня
            console.log(dayButton);
            event.stopPropagation();
            const previouslySelected = document.querySelector('.calendar button.selected');
            if (previouslySelected) {
                previouslySelected.classList.remove('selected');
            }

            // // Проверяем, занят ли выбранный день
            // if (occupiedDays.includes(day)) {
            //     alert("Этот день занят!"); // Или подкрашиваем и выводим сообщение
            //     dayButton.style.backgroundColor = "red"; // Можно также визуально выделить
            // } else {
            //     dayButton.style.backgroundColor = ""; // Сброс цвета для свободных дней
            // }
            // Выделяем выбранный день
            dayButton.classList.add('selected');

        });
        calendar.appendChild(dayButton);

    }

}


let buttonEvents = {}

function getSchedule() {
    const sportSection = document.getElementById("sports-section").value
    const month = parseInt(document.getElementById('month').value)

    const infoBoxes = document.querySelectorAll("div.info-box");
    infoBoxes.forEach(box => box.remove());

    let type;
    let individual = document.getElementById("individual-btn");
    let general = document.getElementById("general-btn");
    if (individual.classList.contains("btn-primary")) {
        type = "individual"
    } else if (general.classList.contains("btn-primary")) {
        type = "general"
    }


    const dayButtons = document.getElementsByName("dayButton");

    for (const dayButton of dayButtons) {
        dayButton.style.backgroundColor = "";
        if (buttonEvents.hasOwnProperty(dayButton.id)) {
            dayButton.removeEventListener('click', buttonEvents[dayButton.id]);
        }
    }


    if (sportSection !== "" && !isNaN(month) && type !== "") {
        fetch("/api/schedule/couch/sport-section/" + sportSection)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
            const countTrainingsDay = {};
            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    if (type === scheduleDay.typeWorkout) {
                        if (!countTrainingsDay.hasOwnProperty(day)) {
                            countTrainingsDay[day] = 1
                        } else {
                            countTrainingsDay[day] += 1
                        }
                    }
                }
            })

            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    const dayButton = calendar.querySelector(`#button_${day}`)
                    let userBase = [];
                    if (dayButton && type === scheduleDay.typeWorkout) {
                        dayButton.style.backgroundColor = "#00FFCC";

                        fetch("/api/booking/couchBooking/")
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error(response.message);
                                }
                                return response.json()

                            }).then(data => {
                            data.forEach(bookingTimes => {
                                const scheduleId = bookingTimes.schedule_id;
                                if (scheduleDay.id === scheduleId) {
                                    dayButton.style.backgroundColor = 'yellow';
                                    userBase.push(bookingTimes.user);

                                }
                            })
                        })
                        console.log(scheduleDay);
                        const listener = function () {
                            const timeSlots = document.getElementById("times-record");
                            timeSlots.innerHTML = '';
                            showDetailsBookingToCouch(scheduleDay, dayButton, userBase);
                        }
                        const showTime = function () {
                            const infoBoxes = document.querySelectorAll("div.info-box");
                            infoBoxes.forEach(box => box.remove());
                            showDetailsBookingTime(dayButton);
                        }
                        if (countTrainingsDay[day] === 1) {
                            dayButton.addEventListener('click', listener);
                            buttonEvents[dayButton.id] = listener;
                        } else if (countTrainingsDay[day] > 1) {
                            dayButton.addEventListener('click', showTime);
                            buttonEvents[dayButton.id] = showTime;
                            countTrainingsDay[day] = 0;
                        }
                    }
                }
            })
        })
    }
}

function showDetailsBookingTime(dayButton) {

    const sportSection = document.getElementById("sports-section").value
    const month = parseInt(document.getElementById('month').value)
    const bookingTime = document.getElementById('allTime');
    const timeSlots = document.getElementById("times-record");
    timeSlots.innerHTML = '';

    let type;
    let individual = document.getElementById("individual-btn");
    let general = document.getElementById("general-btn");
    if (individual.classList.contains("btn-primary")) {
        type = "individual"
    } else if (general.classList.contains("btn-primary")) {
        type = "general"
    }


    fetch("/api/schedule/couch/sport-section/" + sportSection)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json();
        }).then(data => {
        data.forEach(scheduleDay => {
            let userBase = [];
            let scheduleDate = new Date(scheduleDay.date);
            if (scheduleDate.getMonth() === month) {
                let day = scheduleDate.getDate();
                if (parseInt(dayButton.textContent) === day && type === scheduleDay.typeWorkout) {
                    const time = new Date(scheduleDay.date).toLocaleTimeString();
                    const timeButton = document.createElement('buttonTime');
                    timeButton.classList.add('buttonTime');
                    timeButton.textContent = time;
                    timeButton.style.backgroundColor = 'greenYellow';

                    // timeButton.setAttribute('buttonTimeId',scheduleDay.value);

                    fetch("/api/booking/couchBooking/")
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(response.message);
                            }
                            return response.json()

                        }).then(data => {
                        data.forEach(bookingTimes => {
                            const scheduleId = bookingTimes.schedule_id;
                            if (scheduleDay.id === scheduleId) {
                                timeButton.style.backgroundColor = 'yellow';
                                dayButton.style.backgroundColor = 'yellow';
                                userBase.push(bookingTimes.user);
                            }
                        })
                    })
                    const listener = function (event) {
                        event.stopPropagation();
                        showDetailsBookingToCouch(scheduleDay, timeButton, userBase);

                    }
                    timeButton.addEventListener("click", listener);
                    timeSlots.appendChild(timeButton);
                }
            }
        })
    });

}

function showDetailsBookingToCouch(scheduleDay, dayButton, userBase) {

    const schedule = document.getElementById("scheduleForCouch");
    // Создаем или показываем окно с информацией
    const infoBoxes = document.querySelectorAll("div.info-box");
    infoBoxes.forEach(box => box.remove());

    console.log(userBase);
    let infoBox = document.createElement('div');
    infoBox.classList.add('info-box');
    // infoBox.style.display = 'flex';


    const time = new Date(scheduleDay.date).toLocaleTimeString(this.time);
    const description = scheduleDay.description || "Комментарий отсутствует";
    const place = scheduleDay.place || "Место не указано";
    const sum = scheduleDay.sum || "Сумма не указана";
    const scheduleId = scheduleDay.id;


    infoBox.innerHTML += `
        <p><strong>Время:</strong> ${time}</p>
        <p><strong>Место:</strong> ${place}</p>
        <p><strong>Сумма:</strong> ${sum}</p>
        <p><strong>Комментарий:</strong> ${description}</p>`;


    if (dayButton.style.backgroundColor === "yellow") {
        let fio = "";
        userBase.forEach(userData=> {
            fio += `${userData.surname || "Фамилия не указана"} ${userData.name || "Имя не указанно"}, `
        })
        infoBox.innerHTML += `<p><strong>Записанные:</strong><br>${fio.slice(0, -2).replaceAll(', ', '<br>')}</p>`;
        schedule.appendChild(infoBox);

    }
    infoBox.innerHTML += `
        <button id="bookButtonCancel" style="width: 95%" class="btn btn-primary">Отменить</button>
        <button id="closeInfoBox" style="width: 95%" class="btn btn-primary">Закрыть</button>`;


    // Отменить бронирование
    infoBox.querySelector('#bookButtonCancel').addEventListener('click', function () {
        bookButtonCancel(scheduleId, infoBox); // Отменить бронирования
    });

    // Обработчик на кнопку "Закрыть"
    infoBox.querySelector('#closeInfoBox').addEventListener('click', function () {
        schedule.removeChild(infoBox); // Удаляем окно
    });


    const rect = dayButton.getBoundingClientRect();
    const scheduleRect = schedule.getBoundingClientRect();

// Привязываем infoBox к #schedule
    schedule.appendChild(infoBox);
    infoBox.style.position = 'absolute';

// Получаем координаты кнопки относительно schedule
    const scrollTop = schedule.scrollTop;
    const scrollLeft = schedule.scrollLeft;

    let top = rect.bottom - scheduleRect.top + scrollTop ; // 4px отступ вниз
    let left = rect.left - scheduleRect.left + scrollLeft;

// Ширина окна
    const infoBoxWidth = infoBox.offsetWidth;
    const scheduleWidth = schedule.clientWidth;

// Проверяем, выходит ли за правую границу
    if (left + infoBoxWidth > scheduleWidth) {
        left = rect.right - scheduleRect.left + scrollLeft - infoBoxWidth;
        if (left < 0) left = 0;
        infoBox.style.borderRadius = '15px 0 15px 15px'; // левое скругление
    } else {
        infoBox.style.borderRadius = '0 15px 15px 15px'; // правое скругление
    }

    infoBox.style.top = `${top}px`;
    infoBox.style.left = `${left}px`;

    schedule.appendChild(infoBox);

}

async function bookButtonCancel(scheduleId, infoBox) {
    try {
        fetch("/api/schedule/" + scheduleId, {
            method: "DELETE",
            headers: {'Content-Type': 'application/json'},

        }).then(response => {
            getSchedule();
            infoBox.innerHTML = "";
            if (!response.ok) {
                throw new Error("Ошибка при отмене бронирования");
            }
        })
    } catch (error) {
        console.error("Ошибка отмены:", error)
    }
}

document.addEventListener("click", (event) => {

    const infoBoxes = document.querySelectorAll("div.info-box");

    infoBoxes.forEach((box) => {
        if (!box.contains(event.target)) {
            box.remove()
        }
    });

})

function closeConfirmationModal() {
    const hidden = document.getElementById("confirmationModal")
    $("#confirmationModal").modal('hide');
}

function tariffsForCouch() {
    const tariffContainer = document.getElementById("tariffContainer");
    const sportSection = document.getElementById("sports-section").value
    tariffContainer.innerHTML = "";
    console.log(sportSection);
    let sportSectionId;

    fetch("/api/couch/sport-section/")
        .then(response => {
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json();
        }).then(data => {
        data.forEach(sportSection => {
            sportSectionId = sportSection.id;
        })
        fetch("/api/seasonTickets/ticket/get/" + sportSectionId)
        .then(response => {
            if (!response.ok) {
                throw new Error("нету такого тарифа")
            }
            return response.json()
        })
        .then(data => {
            data.forEach(item => {
                const tariffs = document.createElement("div");
                tariffs.classList.add("tariffs");
                tariffs.display = 'flex';
                const ticketId = item.id;
                console.log(ticketId);
                const name = item.name || "Не указано";
                const description = item.description || "Не указано";
                const days = item.days || "Не указано";
                const sum = item.sum || "Не указано";
                let schedule = "";
                item.date.forEach(date => {
                    schedule += `${date.dayOfWeek} в ${date.time}, `
                })
                const uuid = item.uuid;

                tariffs.innerHTML += `
            <p><strong>${name}</strong> </p>
            <p>${description}</p>
            <p><strong>Сумма:</strong> ${sum} руб, ${days} дней</p>
            <p><strong>Расписание:</strong> ${schedule.slice(0, -2)}</p>
            <button class="btn btn-success deleteGeneral">Удалить</button>
            `

                tariffs.querySelector('.deleteGeneral').addEventListener('click', function () {
                    if (confirm("Подтверждаете удаление?")){
                        // метод где будет вызываться пост запрос и передавать туда item.uuid
                        fetch("/api/seasonTickets/deleteTicket"+ticketId, {
                            method:'DELETE',
                            headers: {'Content-Type': 'application/json'},
                        })
                            .then(response =>{
                                if (!response.ok){
                                    throw new Error(response.message);
                                }
                                return response;

                            })
                        tariffs.remove();
                    }
                })

                tariffContainer.appendChild(tariffs);
            })


        });
    })

}
